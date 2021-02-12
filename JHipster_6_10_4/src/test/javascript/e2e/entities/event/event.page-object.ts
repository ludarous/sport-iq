import { element, by, ElementFinder } from 'protractor';

export class EventComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-event div table .btn-danger'));
  title = element.all(by.css('jhi-event div h2#page-heading span')).first();
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

export class EventUpdatePage {
  pageTitle = element(by.id('jhi-event-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  dateInput = element(by.id('field_date'));

  eventLocationSelect = element(by.id('field_eventLocation'));
  activitiesSelect = element(by.id('field_activities'));
  entrantsSelect = element(by.id('field_entrants'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setDateInput(date: string): Promise<void> {
    await this.dateInput.sendKeys(date);
  }

  async getDateInput(): Promise<string> {
    return await this.dateInput.getAttribute('value');
  }

  async eventLocationSelectLastOption(): Promise<void> {
    await this.eventLocationSelect.all(by.tagName('option')).last().click();
  }

  async eventLocationSelectOption(option: string): Promise<void> {
    await this.eventLocationSelect.sendKeys(option);
  }

  getEventLocationSelect(): ElementFinder {
    return this.eventLocationSelect;
  }

  async getEventLocationSelectedOption(): Promise<string> {
    return await this.eventLocationSelect.element(by.css('option:checked')).getText();
  }

  async activitiesSelectLastOption(): Promise<void> {
    await this.activitiesSelect.all(by.tagName('option')).last().click();
  }

  async activitiesSelectOption(option: string): Promise<void> {
    await this.activitiesSelect.sendKeys(option);
  }

  getActivitiesSelect(): ElementFinder {
    return this.activitiesSelect;
  }

  async getActivitiesSelectedOption(): Promise<string> {
    return await this.activitiesSelect.element(by.css('option:checked')).getText();
  }

  async entrantsSelectLastOption(): Promise<void> {
    await this.entrantsSelect.all(by.tagName('option')).last().click();
  }

  async entrantsSelectOption(option: string): Promise<void> {
    await this.entrantsSelect.sendKeys(option);
  }

  getEntrantsSelect(): ElementFinder {
    return this.entrantsSelect;
  }

  async getEntrantsSelectedOption(): Promise<string> {
    return await this.entrantsSelect.element(by.css('option:checked')).getText();
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

export class EventDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-event-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-event'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
