package com.tratra.tratra_app.service;

import com.tratra.tratra_app.entity.Activity;
import com.tratra.tratra_app.entity.User;
import com.tratra.tratra_app.repository.ActivityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

@Service
public class ActivityService {

    private static final String GPX_UPLOAD_DIR = "uploads/gpx/";

    @Autowired
    private ActivityRepository activityRepository;

    public void saveActivity(String name, String description, String dateStr, String activityType, MultipartFile gpxFile, User user) {
        try {
            Path uploadPath = Paths.get(GPX_UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            if (gpxFile == null || gpxFile.isEmpty()) {
                throw new IllegalArgumentException("Archivo GPX no puede estar vacío");
            }

            String originalFilename = Path.of(gpxFile.getOriginalFilename()).getFileName().toString();
            String filename = System.currentTimeMillis() + "_" + originalFilename.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");

            Path filePath = uploadPath.resolve(filename);
            Files.copy(gpxFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            LocalDate date = LocalDate.parse(dateStr);

            // Leer coordenadas y elevaciones del archivo
            List<double[]> points = parseGpxFileToCoordinatesWithElevation(filePath.toAbsolutePath().toString());

            double distanceKm = calculateTotalDistance(points);
            double elevationGain = calculateElevationGain(points);

            Activity activity = new Activity();
            activity.setName(name);
            activity.setDescription(description);
            activity.setDate(date);
            activity.setGpxFilePath(filePath.toAbsolutePath().toString());
            activity.setUser(user);
            activity.setActivityType(activityType);
            activity.setDistanceKm(distanceKm);
            activity.setElevationGain(elevationGain);

            activityRepository.save(activity);

        } catch (IOException e) {
            throw new RuntimeException("Error guardando el archivo GPX", e);
        } catch (Exception e) {
            throw new RuntimeException("Error procesando la actividad: " + e.getMessage(), e);
        }
    }

    public List<Activity> findByUsername(String username) {
        return activityRepository.findByUserUsername(username);
    }

    public Optional<Activity> findById(Long id) {
        return activityRepository.findById(id);
    }

    public List<Activity> findAllByUser(User user) {
        return activityRepository.findAllByUser(user);
    }

    // Método extendido para obtener latitud, longitud y elevación
    public List<double[]> parseGpxFileToCoordinatesWithElevation(String gpxFilePath) {
        List<double[]> coordinates = new ArrayList<>();
        try {
            File gpxFile = new File(gpxFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            var dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(gpxFile);

            doc.getDocumentElement().normalize();

            NodeList trkptList = doc.getElementsByTagName("trkpt");

            for (int i = 0; i < trkptList.getLength(); i++) {
                Node trkptNode = trkptList.item(i);
                if (trkptNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element trkptElement = (Element) trkptNode;
                    double lat = Double.parseDouble(trkptElement.getAttribute("lat"));
                    double lon = Double.parseDouble(trkptElement.getAttribute("lon"));
                    double ele = 0.0;

                    NodeList children = trkptElement.getElementsByTagName("ele");
                    if (children.getLength() > 0) {
                        ele = Double.parseDouble(children.item(0).getTextContent());
                    }

                    coordinates.add(new double[]{lat, lon, ele});
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error leyendo el archivo GPX: " + e.getMessage(), e);
        }
        return coordinates;
    }

    // Calcula distancia total en kilómetros usando la fórmula de Haversine
    public double calculateTotalDistance(List<double[]> coords) {
        double total = 0.0;
        for (int i = 1; i < coords.size(); i++) {
            total += haversine(coords.get(i - 1), coords.get(i));
        }
        return total;
    }

    // Fórmula de Haversine para calcular distancia entre dos puntos geográficos
    private double haversine(double[] p1, double[] p2) {
        double R = 6371.0; // radio de la Tierra en km
        double lat1 = Math.toRadians(p1[0]);
        double lon1 = Math.toRadians(p1[1]);
        double lat2 = Math.toRadians(p2[0]);
        double lon2 = Math.toRadians(p2[1]);

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
                   Math.cos(lat1) * Math.cos(lat2) *
                   Math.sin(dlon / 2) * Math.sin(dlon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    // Calcula desnivel positivo (suma de todos los ascensos)
    public double calculateElevationGain(List<double[]> coords) {
        double gain = 0.0;
        for (int i = 1; i < coords.size(); i++) {
            double diff = coords.get(i)[2] - coords.get(i - 1)[2];
            if (diff > 0) {
                gain += diff;
            }
        }
        return gain;
    }
}
