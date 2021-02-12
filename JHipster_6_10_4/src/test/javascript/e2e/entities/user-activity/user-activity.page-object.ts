import { element, by, ElementFinder } from 'protractor';

export class UserActivityComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-user-activity div table .btn-danger'));
  title = element.all(by.css('jhi-user-activity div h2#page-heading span')).first();
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

export class UserActivityUpdatePage {
  pageTitle = element(by.id('jhi-user-activity-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  noteInput = element(by.id('field_note'));
  dateInput = element(by.id('field_date'));

  activitySelect = element(by.id('field_activity'));
  userEventSelect = element(by.id('field_userEvent'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setNoteInput(note: string): Promise<void> {
    await this.noteInput.sendKeys(note);
  }

  async getNoteInput(): Promise<string> {
    return await this.noteInput.getAttribute('value');
  }

  async setDateInput(date: string): Promise<void> {
    await this.dateInput.sendKeys(date);
  }

  async getDateInput(): Promise<string> {
    return await this.dateInput.getAttribute('value');
  }

  async activitySelectLastOption(): Promise<void> {
    await this.activitySelect.all(by.tagName('option')).last().click();
  }

  async activitySelectOption(option: string): Promise<void> {
    await this.activitySelect.sendKeys(option);
  }

  getActivitySelect(): ElementFinder {
    return this.activitySelect;
  }

  async getActivitySelectedOption(): Promise<string> {
    return await this.activitySelect.element(by.css('option:checked')).getText();
  }

  async userEventSelectLastOption(): Promise<void> {
    await this.userEventSelect.all(by.tagName('option')).last().click();
  }

  async userEventSelectOption(option: string): Promise<void> {
    await this.userEventSelect.sendKeys(option);
  }

  getUserEventSelect(): ElementFinder {
    return this.userEventSelect;
  }

  async getUserEventSelectedOption(): Promise<string> {
    return await this.userEventSelect.element(by.css('option:checked')).getText();
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

export class UserActivityDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-userActivity-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-userActivity'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
