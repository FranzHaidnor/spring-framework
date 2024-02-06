package org.example;

import org.springframework.asm.ClassReader;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.SimpleAnnotationMetadata;
import org.springframework.core.type.classreading.SimpleAnnotationMetadataReadingVisitor;

import java.io.IOException;
import java.io.InputStream;

public class MetadataTest {
	public static void main(String[] args) throws IOException {

		PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
		Resource resource = patternResolver.getResource("classpath:" + "org/example/config/BeanConfiguration.class");
		ClassReader classReader = getClassReader(resource);
		SimpleAnnotationMetadataReadingVisitor visitor = new SimpleAnnotationMetadataReadingVisitor(MetadataTest.class.getClassLoader());
		classReader.accept(visitor, ClassReader.SKIP_DEBUG | ClassReader.SKIP_CODE | ClassReader.SKIP_FRAMES);
		SimpleAnnotationMetadata metadata = visitor.getMetadata();

		System.out.println(metadata);
	}


	private static ClassReader getClassReader(Resource resource) throws IOException {
		try (InputStream is = resource.getInputStream()) {
			try {
				// 创建类读取器
				return new ClassReader(is);
			} catch (IllegalArgumentException ex) {
				throw new NestedIOException("ASM ClassReader failed to parse class file - " +
						"probably due to a new Java class file version that isn't supported yet: " + resource, ex);
			}
		}
	}

}
