import { element, by, ElementFinder } from 'protractor';

export class EventLocationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-event-location div table .btn-danger'));
  title = element.all(by.css('jhi-event-location div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getText();
  }
}

export class EventLocationUpdatePage {
  pageTitle = element(by.id('jhi-event-location-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  stateInput = element(by.id('field_state'));
  cityInput = element(by.id('field_city'));
  streetInput = element(by.id('field_street'));
  streetNumberInput = element(by.id('field_streetNumber'));
  latitudeInput = element(by.id('field_latitude'));
  longitudeInput = element(by.id('field_longitude'));
  capacityInput = element(by.id('field_capacity'));
  mapLinkInput = element(by.id('field_mapLink'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setStateInput(state: string): Promise<void> {
    await this.stateInput.sendKeys(state);
  }

  async getStateInput(): Promise<string> {
    return await this.stateInput.getAttribute('value');
  }

  async setCityInput(city: string): Promise<void> {
    await this.cityInput.sendKeys(city);
  }

  async getCityInput(): Promise<string> {
    return await this.cityInput.getAttribute('value');
  }

  async setStreetInput(street: string): Promise<void> {
    await this.streetInput.sendKeys(street);
  }

  async getStreetInput(): Promise<string> {
    return await this.streetInput.getAttribute('value');
  }

  async setStreetNumberInput(streetNumber: string): Promise<void> {
    await this.streetNumberInput.sendKeys(streetNumber);
  }

  async getStreetNumberInput(): Promise<string> {
    return await this.streetNumberInput.getAttribute('value');
  }

  async setLatitudeInput(latitude: string): Promise<void> {
    await this.latitudeInput.sendKeys(latitude);
  }

  async getLatitudeInput(): Promise<string> {
    return await this.latitudeInput.getAttribute('value');
  }

  async setLongitudeInput(longitude: string): Promise<void> {
    await this.longitudeInput.sendKeys(longitude);
  }

  async getLongitudeInput(): Promise<string> {
    return await this.longitudeInput.getAttribute('value');
  }

  async setCapacityInput(capacity: string): Promise<void> {
    await this.capacityInput.sendKeys(capacity);
  }

  async getCapacityInput(): Promise<string> {
    return await this.capacityInput.getAttribute('value');
  }

  async setMapLinkInput(mapLink: string): Promise<void> {
    await this.mapLinkInput.sendKeys(mapLink);
  }

  async getMapLinkInput(): Promise<string> {
    return await this.mapLinkInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class EventLocationDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-eventLocation-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-eventLocation'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
