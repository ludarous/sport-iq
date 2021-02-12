import { element, by, ElementFinder } from 'protractor';

export class ActivityResultComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-activity-result div table .btn-danger'));
  title = element.all(by.css('jhi-activity-result div h2#page-heading span')).first();
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

export class ActivityResultUpdatePage {
  pageTitle = element(by.id('jhi-activity-result-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  resultTypeSelect = element(by.id('field_resultType'));
  ratingWeightInput = element(by.id('field_ratingWeight'));
  mainResultInput = element(by.id('field_mainResult'));
  orderInput = element(by.id('field_order'));
  irvBestInput = element(by.id('field_irvBest'));
  irvWorstInput = element(by.id('field_irvWorst'));

  resultUnitSelect = element(by.id('field_resultUnit'));
  activitySelect = element(by.id('field_activity'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setResultTypeSelect(resultType: string): Promise<void> {
    await this.resultTypeSelect.sendKeys(resultType);
  }

  async getResultTypeSelect(): Promise<string> {
    return await this.resultTypeSelect.element(by.css('option:checked')).getText();
  }

  async resultTypeSelectLastOption(): Promise<void> {
    await this.resultTypeSelect.all(by.tagName('option')).last().click();
  }

  async setRatingWeightInput(ratingWeight: string): Promise<void> {
    await this.ratingWeightInput.sendKeys(ratingWeight);
  }

  async getRatingWeightInput(): Promise<string> {
    return await this.ratingWeightInput.getAttribute('value');
  }

  getMainResultInput(): ElementFinder {
    return this.mainResultInput;
  }

  async setOrderInput(order: string): Promise<void> {
    await this.orderInput.sendKeys(order);
  }

  async getOrderInput(): Promise<string> {
    return await this.orderInput.getAttribute('value');
  }

  async setIrvBestInput(irvBest: string): Promise<void> {
    await this.irvBestInput.sendKeys(irvBest);
  }

  async getIrvBestInput(): Promise<string> {
    return await this.irvBestInput.getAttribute('value');
  }

  async setIrvWorstInput(irvWorst: string): Promise<void> {
    await this.irvWorstInput.sendKeys(irvWorst);
  }

  async getIrvWorstInput(): Promise<string> {
    return await this.irvWorstInput.getAttribute('value');
  }

  async resultUnitSelectLastOption(): Promise<void> {
    await this.resultUnitSelect.all(by.tagName('option')).last().click();
  }

  async resultUnitSelectOption(option: string): Promise<void> {
    await this.resultUnitSelect.sendKeys(option);
  }

  getResultUnitSelect(): ElementFinder {
    return this.resultUnitSelect;
  }

  async getResultUnitSelectedOption(): Promise<string> {
    return await this.resultUnitSelect.element(by.css('option:checked')).getText();
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

export class ActivityResultDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-activityResult-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-activityResult'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
