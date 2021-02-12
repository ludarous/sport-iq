import { element, by, ElementFinder } from 'protractor';

export class UserActivityResultSplitComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-user-activity-result-split div table .btn-danger'));
  title = element.all(by.css('jhi-user-activity-result-split div h2#page-heading span')).first();
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

export class UserActivityResultSplitUpdatePage {
  pageTitle = element(by.id('jhi-user-activity-result-split-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  valueInput = element(by.id('field_value'));
  compareValueInput = element(by.id('field_compareValue'));

  activityResultSplitSelect = element(by.id('field_activityResultSplit'));
  userActivityResultSelect = element(by.id('field_userActivityResult'));

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

  async activityResultSplitSelectLastOption(): Promise<void> {
    await this.activityResultSplitSelect.all(by.tagName('option')).last().click();
  }

  async activityResultSplitSelectOption(option: string): Promise<void> {
    await this.activityResultSplitSelect.sendKeys(option);
  }

  getActivityResultSplitSelect(): ElementFinder {
    return this.activityResultSplitSelect;
  }

  async getActivityResultSplitSelectedOption(): Promise<string> {
    return await this.activityResultSplitSelect.element(by.css('option:checked')).getText();
  }

  async userActivityResultSelectLastOption(): Promise<void> {
    await this.userActivityResultSelect.all(by.tagName('option')).last().click();
  }

  async userActivityResultSelectOption(option: string): Promise<void> {
    await this.userActivityResultSelect.sendKeys(option);
  }

  getUserActivityResultSelect(): ElementFinder {
    return this.userActivityResultSelect;
  }

  async getUserActivityResultSelectedOption(): Promise<string> {
    return await this.userActivityResultSelect.element(by.css('option:checked')).getText();
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

export class UserActivityResultSplitDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-userActivityResultSplit-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-userActivityResultSplit'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
