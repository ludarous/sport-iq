import { element, by, ElementFinder } from 'protractor';

export class UserActivityResultComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-user-activity-result div table .btn-danger'));
  title = element.all(by.css('jhi-user-activity-result div h2#page-heading span')).first();
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

export class UserActivityResultUpdatePage {
  pageTitle = element(by.id('jhi-user-activity-result-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  valueInput = element(by.id('field_value'));
  compareValueInput = element(by.id('field_compareValue'));

  activityResultSelect = element(by.id('field_activityResult'));
  userActivitySelect = element(by.id('field_userActivity'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setValueInput(value: string): Promise<void> {
    await this.valueInput.sendKeys(value);
  }

  async getValueInput(): Promise<string> {
    return await this.valueInput.getAttribute('value');
  }

  async setCompareValueInput(compareValue: string): Promise<void> {
    await this.compareValueInput.sendKeys(compareValue);
  }

  async getCompareValueInput(): Promise<string> {
    return await this.compareValueInput.getAttribute('value');
  }

  async activityResultSelectLastOption(): Promise<void> {
    await this.activityResultSelect.all(by.tagName('option')).last().click();
  }

  async activityResultSelectOption(option: string): Promise<void> {
    await this.activityResultSelect.sendKeys(option);
  }

  getActivityResultSelect(): ElementFinder {
    return this.activityResultSelect;
  }

  async getActivityResultSelectedOption(): Promise<string> {
    return await this.activityResultSelect.element(by.css('option:checked')).getText();
  }

  async userActivitySelectLastOption(): Promise<void> {
    await this.userActivitySelect.all(by.tagName('option')).last().click();
  }

  async userActivitySelectOption(option: string): Promise<void> {
    await this.userActivitySelect.sendKeys(option);
  }

  getUserActivitySelect(): ElementFinder {
    return this.userActivitySelect;
  }

  async getUserActivitySelectedOption(): Promise<string> {
    return await this.userActivitySelect.element(by.css('option:checked')).getText();
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

export class UserActivityResultDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-userActivityResult-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-userActivityResult'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
