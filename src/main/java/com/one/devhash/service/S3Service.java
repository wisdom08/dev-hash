package com.one.devhash.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.one.devhash.domain.ImageTarget;
import com.one.devhash.domain.Imagefile;
import com.one.devhash.global.error.exception.EntityNotFoundException;
import com.one.devhash.global.error.exception.ErrorCode;
import com.one.devhash.repository.ImagefileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {
	private final ImagefileRepository imagefileRepository;

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public void uploadFile(MultipartFile[] files, String directory, Long productId) {
		ObjectMetadata omd = new ObjectMetadata();
		for (MultipartFile file : files) {
			String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.KOREA))
					+ "_" + Objects.requireNonNull(file.getOriginalFilename()).toLowerCase();
			if(!(fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith("jpeg"))) {
				throw new EntityNotFoundException(ErrorCode.UNSUPPORTED_FILE_FORMAT);
			}
			omd.setContentType(file.getContentType());
			omd.setContentLength(file.getSize());
			omd.setHeader("filename", file.getOriginalFilename());
			try {
				amazonS3.putObject(new PutObjectRequest(bucket + "/" + directory,
						fileName, file.getInputStream(), omd)
						.withCannedAcl(CannedAccessControlList.PublicRead));
			} catch (IOException e) {
				throw new EntityNotFoundException(ErrorCode.CONVERTING_FAILED);
			}
			String imagePath = amazonS3.getUrl(bucket + "/" + directory, fileName).toString();
			Imagefile image = Imagefile.builder()
					.imageTarget(ImageTarget.PRODUCT)
					.targetId(productId)
					.imageUrl(imagePath)
					.build();
			imagefileRepository.save(image);
		}
	}

	public void deleteFile(ImageTarget target, Long targetId) {
		List<Imagefile> imagefiles = imagefileRepository.findAllByTargetId(target, targetId);
		if(imagefiles.size() != 0) {
			for (Imagefile imagefile : imagefiles) {
				String url = imagefile.getImageUrl();
				amazonS3.deleteObject(bucket, url.split(bucket + "/", 2)[1]);
			}
		}
		imagefileRepository.deleteAllByTargetId(target, targetId);
	}
}