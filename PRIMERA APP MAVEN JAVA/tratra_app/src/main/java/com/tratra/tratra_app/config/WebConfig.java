package com.tratra.tratra_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Ruta absoluta en formato URI para exponer la carpeta de archivos GPX
        String gpxUploadPath = Paths.get("uploads/gpx/").toAbsolutePath().toUri().toString();
        registry.addResourceHandler("/uploads/gpx/**")
                .addResourceLocations(gpxUploadPath);

        // Ruta absoluta para la carpeta de avatares
        String avatarUploadPath = Paths.get("uploads/avatars/").toAbsolutePath().toUri().toString();
        registry.addResourceHandler("/uploads/avatars/**")
                .addResourceLocations(avatarUploadPath);
    }
}
