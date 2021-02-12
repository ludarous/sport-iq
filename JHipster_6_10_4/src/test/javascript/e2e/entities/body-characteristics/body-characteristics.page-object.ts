import { element, by, ElementFinder } from 'protractor';

export class BodyCharacteristicsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-body-characteristics div table .btn-danger'));
  title = element.all(by.css('jhi-body-characteristics div h2#page-heading span')).first();
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

export class BodyCharacteristicsUpdatePage {
  pageTitle = element(by.id('jhi-body-characteristics-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  heightInput = element(by.id('field_height'));
  weightInput = element(by.id('field_weight'));
  dateInput = element(by.id('field_date'));

  heightUnitSelect = element(by.id('field_heightUnit'));
  widthUnitSelect = element(by.id('field_widthUnit'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setHeightInput(height: string): Promise<void> {
    await this.heightInput.sendKeys(height);
  }

  async getHeightInput(): Promise<string> {
    return await this.heightInput.getAttribute('value');
  }

  async setWeightInput(weight: string): Promise<void> {
    await this.weightInput.sendKeys(weight);
  }

  async getWeightInput(): Promise<string> {
    return await this.weightInput.getAttribute('value');
  }

  async setDateInput(date: string): Promise<void> {
    await this.dateInput.sendKeys(date);
  }

  async getDateInput(): Promise<string> {
    return await this.dateInput.getAttribute('value');
  }

  async heightUnitSelectLastOption(): Promise<void> {
    await this.heightUnitSelect.all(by.tagName('option')).last().click();
  }

  async heightUnitSelectOption(option: string): Promise<void> {
    await this.heightUnitSelect.sendKeys(option);
  }

  getHeightUnitSelect(): ElementFinder {
    return this.heightUnitSelect;
  }

  async getHeightUnitSelectedOption(): Promise<string> {
    return await this.heightUnitSelect.element(by.css('option:checked')).getText();
  }

  async widthUnitSelectLastOption(): Promise<void> {
    await this.widthUnitSelect.all(by.tagName('option')).last().click();
  }

  async widthUnitSelectOption(option: string): Promise<void> {
    await this.widthUnitSelect.sendKeys(option);
  }

  getWidthUnitSelect(): ElementFinder {
    return this.widthUnitSelect;
  }

  async getWidthUnitSelectedOption(): Promise<string> {
    return await this.widthUnitSelect.element(by.css('option:checked')).getText();
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

export class BodyCharacteristicsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-bodyCharacteristics-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-bodyCharacteristics'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
