package ru.netology.patient.service.medical;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;

import java.math.BigDecimal;
import java.time.LocalDate;

class MedicalServiceImplTest {

    private PatientInfoRepository patientInfoRepository;
    private SendAlertService alertService;
    private Object BloodPressure;

    @Test
    void checkBloodPressureWithSendAlert() {
        patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        alertService = Mockito.mock(SendAlertService.class);

        BloodPressure bloodPressure = new BloodPressure(125, 80);
        BloodPressure expectedBloodPressure = new BloodPressure(120, 80);
        BigDecimal bigDecimal = new BigDecimal("36.65");

        Mockito.when(patientInfoRepository.getById("1"))
                .thenReturn(new PatientInfo("1",
                        "Ivan", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(bigDecimal, bloodPressure)));
        PatientInfo patientInfo = patientInfoRepository.getById("1");
        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);


        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkBloodPressure("1", expectedBloodPressure);

        Mockito.verify(alertService, Mockito.times(1)).send(message);
    }

    @Test
    void checkBloodPressureWithNormalValue() {
        patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        alertService = Mockito.mock(SendAlertService.class);

        BloodPressure bloodPressure = new BloodPressure(120, 80);
        BloodPressure expectedBloodPressure = new BloodPressure(120, 80);
        BigDecimal bigDecimal = new BigDecimal("36.65");

        Mockito.when(patientInfoRepository.getById("1"))
                .thenReturn(new PatientInfo("1",
                        "Ivan", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(bigDecimal, bloodPressure)));
        PatientInfo patientInfo = patientInfoRepository.getById("1");
        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkBloodPressure("1", expectedBloodPressure);

        Mockito.verify(alertService, Mockito.times(0)).send(message);
    }

    @Test
    void checkTemperatureWithSendAlert() {
        patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        alertService = Mockito.mock(SendAlertService.class);

        BloodPressure bloodPressure = new BloodPressure(120, 80);
        BigDecimal expectedTemperature = new BigDecimal("35.10");
        BigDecimal bigDecimal = new BigDecimal("36.65");

        Mockito.when(patientInfoRepository.getById("1"))
                .thenReturn(new PatientInfo("1",
                        "Ivan", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(bigDecimal, bloodPressure)));
        PatientInfo patientInfo = patientInfoRepository.getById("1");
        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkTemperature("1", expectedTemperature);

        Mockito.verify(alertService, Mockito.times(1)).send(message);
    }

    @Test
    void checkTemperatureWithNormalValue() {
        patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        alertService = Mockito.mock(SendAlertService.class);

        BloodPressure bloodPressure = new BloodPressure(120, 80);
        BigDecimal expectedTemperature = new BigDecimal("36.65");
        BigDecimal bigDecimal = new BigDecimal("36.65");

        Mockito.when(patientInfoRepository.getById("1"))
                .thenReturn(new PatientInfo("1",
                        "Ivan", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(bigDecimal, bloodPressure)));
        PatientInfo patientInfo = patientInfoRepository.getById("1");
        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkTemperature("1", expectedTemperature);

        Mockito.verify(alertService, Mockito.times(0)).send(message);
    }
}