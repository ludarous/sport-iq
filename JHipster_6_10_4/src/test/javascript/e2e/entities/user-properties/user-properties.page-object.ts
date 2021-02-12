import { element, by, ElementFinder } from 'protractor';

export class UserPropertiesComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-user-properties div table .btn-danger'));
  title = element.all(by.css('jhi-user-properties div h2#page-heading span')).first();
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

export class UserPropertiesUpdatePage {
  pageTitle = element(by.id('jhi-user-properties-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  birthDateInput = element(by.id('field_birthDate'));
  phoneInput = element(by.id('field_phone'));
  nationalityInput = element(by.id('field_nationality'));
  sexSelect = element(by.id('field_sex'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setBirthDateInput(birthDate: string): Promise<void> {
    await this.birthDateInput.sendKeys(birthDate);
  }

  async getBirthDateInput(): Promise<string> {
    return await this.birthDateInput.getAttribute('value');
  }

  async setPhoneInput(phone: string): Promise<void> {
    await this.phoneInput.sendKeys(phone);
  }

  async getPhoneInput(): Promise<string> {
    return await this.phoneInput.getAttribute('value');
  }

  async setNationalityInput(nationality: string): Promise<void> {
    await this.nationalityInput.sendKeys(nationality);
  }

  async getNationalityInput(): Promise<string> {
    return await this.nationalityInput.getAttribute('value');
  }

  async setSexSelect(sex: string): Promise<void> {
    await this.sexSelect.sendKeys(sex);
  }

  async getSexSelect(): Promise<string> {
    return await this.sexSelect.element(by.css('option:checked')).getText();
  }

  async sexSelectLastOption(): Promise<void> {
    await this.sexSelect.all(by.tagName('option')).last().click();
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

export class UserPropertiesDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-userProperties-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-userProperties'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
