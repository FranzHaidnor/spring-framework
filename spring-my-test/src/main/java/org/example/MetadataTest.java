package org.example;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;

public class MetadataTest {
	public static void main(String[] args) throws IOException {
	}

	public void test_() throws Exception {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);

		MetadataReader metadataReader = metadataReaderFactory.getMetadataReader("com.test.Demo");
		// 获取注解元数据
		AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
		// 获取类元数据
		ClassMetadata classMetadata = metadataReader.getClassMetadata();
	}

}
