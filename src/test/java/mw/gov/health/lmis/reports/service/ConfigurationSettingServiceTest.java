package mw.gov.health.lmis.reports.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import mw.gov.health.lmis.reports.exception.NotFoundMessageException;
import mw.gov.health.lmis.settings.domain.ConfigurationSetting;
import mw.gov.health.lmis.settings.exception.ConfigurationSettingException;
import mw.gov.health.lmis.settings.repository.ConfigurationSettingRepository;
import mw.gov.health.lmis.settings.service.ConfigurationSettingService;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationSettingServiceTest {

  @Mock
  private ConfigurationSettingRepository configurationSettingRepository;

  @InjectMocks
  private ConfigurationSettingService configurationSettingService;

  private ConfigurationSetting configurationSetting;

  @Before
  public void setUp() {
    generateInstances();
    mockRepositories();
  }

  @Test
  public void shouldGetConfigurationSettingByKeyIfKeyExists() throws ConfigurationSettingException {
    assertEquals(configurationSettingService.getByKey("key"), configurationSetting);
  }

  @Test(expected = NotFoundMessageException.class)
  public void shouldGetConfigurationSettingByKeyIfDoesNotKeyExists()
      throws NotFoundMessageException {
    configurationSettingService.getByKey("testEmpty");
  }

  @Test
  public void shouldGetValueIfKeyExists() throws ConfigurationSettingException {
    assertEquals(configurationSettingService.getStringValue("key"), "value");
  }

  @Test(expected = NotFoundMessageException.class)
  public void shouldThrowExceptionIfKeyDoesNotExists() throws NotFoundMessageException {
    configurationSettingService.getStringValue("testEmpty");
  }

  @Test
  public void shouldCatchExceptionAndReturnFalseIfKeyDoesNotExists()
      throws ConfigurationSettingException {
    assertEquals(configurationSettingService.getBoolValue("testEmpty"), Boolean.FALSE);
  }

  @Test
  public void shouldGetBoolTrueValueIfKeyExists() {
    configurationSetting = new ConfigurationSetting();
    configurationSetting.setKey("testTrue");
    configurationSetting.setValue(Boolean.TRUE.toString());
    when(configurationSettingRepository
        .findOne(configurationSetting.getKey()))
        .thenReturn(configurationSetting);
    assertEquals(configurationSettingService.getBoolValue("testTrue"), Boolean.TRUE);
  }

  @Test
  public void shouldGetBoolFalseValueIfKeyExists() {
    ConfigurationSetting setting = new ConfigurationSetting();
    setting.setKey("testFalse");
    setting.setValue(Boolean.FALSE.toString());
    when(configurationSettingRepository
        .findOne(configurationSetting.getKey()))
        .thenReturn(configurationSetting);
    assertEquals(configurationSettingService.getBoolValue("testFalse"), Boolean.FALSE);
  }

  private void generateInstances() {
    configurationSetting = new ConfigurationSetting();
    configurationSetting.setKey("key");
    configurationSetting.setValue("value");
  }

  private void mockRepositories() {
    when(configurationSettingRepository
        .findOne(configurationSetting.getKey()))
        .thenReturn(configurationSetting);
  }
}
