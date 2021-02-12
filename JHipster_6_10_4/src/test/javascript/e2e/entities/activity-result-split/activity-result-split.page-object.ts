import { element, by, ElementFinder } from 'protractor';

export class ActivityResultSplitComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-activity-result-split div table .btn-danger'));
  title = element.all(by.css('jhi-activity-result-split div h2#page-heading span')).first();
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

export class ActivityResultSplitUpdatePage {
  pageTitle = element(by.id('jhi-activity-result-split-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  splitValueInput = element(by.id('field_splitValue'));

  splitUnitSelect = element(by.id('field_splitUnit'));
  activityResultSelect = element(by.id('field_activityResult'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setSplitValueInput(splitValue: string): Promise<void> {
    await this.splitValueInput.sendKeys(splitValue);
  }

  async getSplitValueInput(): Promise<string> {
    return await this.splitValueInput.getAttribute('value');
  }

  async splitUnitSelectLastOption(): Promise<void> {
    await this.splitUnitSelect.all(by.tagName('option')).last().click();
  }

  async splitUnitSelectOption(option: string): Promise<void> {
    await this.splitUnitSelect.sendKeys(option);
  }

  getSplitUnitSelect(): ElementFinder {
    return this.splitUnitSelect;
  }

  async getSplitUnitSelectedOption(): Promise<string> {
    return await this.splitUnitSelect.element(by.css('option:checked')).getText();
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

export class ActivityResultSplitDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-activityResultSplit-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-activityResultSplit'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
