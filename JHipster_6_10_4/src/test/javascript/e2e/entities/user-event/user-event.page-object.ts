import { element, by, ElementFinder } from 'protractor';

export class UserEventComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-user-event div table .btn-danger'));
  title = element.all(by.css('jhi-user-event div h2#page-heading span')).first();
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

export class UserEventUpdatePage {
  pageTitle = element(by.id('jhi-user-event-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  noteInput = element(by.id('field_note'));
  registrationDateInput = element(by.id('field_registrationDate'));

  userSelect = element(by.id('field_user'));
  eventSelect = element(by.id('field_event'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setNoteInput(note: string): Promise<void> {
    await this.noteInput.sendKeys(note);
  }

  async getNoteInput(): Promise<string> {
    return await this.noteInput.getAttribute('value');
  }

  async setRegistrationDateInput(registrationDate: string): Promise<void> {
    await this.registrationDateInput.sendKeys(registrationDate);
  }

  async getRegistrationDateInput(): Promise<string> {
    return await this.registrationDateInput.getAttribute('value');
  }

  async userSelectLastOption(): Promise<void> {
    await this.userSelect.all(by.tagName('option')).last().click();
  }

  async userSelectOption(option: string): Promise<void> {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption(): Promise<string> {
    return await this.userSelect.element(by.css('option:checked')).getText();
  }

  async eventSelectLastOption(): Promise<void> {
    await this.eventSelect.all(by.tagName('option')).last().click();
  }

  async eventSelectOption(option: string): Promise<void> {
    await this.eventSelect.sendKeys(option);
  }

  getEventSelect(): ElementFinder {
    return this.eventSelect;
  }

  async getEventSelectedOption(): Promise<string> {
    return await this.eventSelect.element(by.css('option:checked')).getText();
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

export class UserEventDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-userEvent-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-userEvent'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
