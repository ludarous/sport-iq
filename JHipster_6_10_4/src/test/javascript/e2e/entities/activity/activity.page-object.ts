import { element, by, ElementFinder } from 'protractor';

export class ActivityComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-activity div table .btn-danger'));
  title = element.all(by.css('jhi-activity div h2#page-heading span')).first();
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

export class ActivityUpdatePage {
  pageTitle = element(by.id('jhi-activity-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  isPublicInput = element(by.id('field_isPublic'));
  descriptionInput = element(by.id('field_description'));
  helpInput = element(by.id('field_help'));

  createdBySelect = element(by.id('field_createdBy'));
  visibleForOrganisationsSelect = element(by.id('field_visibleForOrganisations'));
  visibleForGroupsSelect = element(by.id('field_visibleForGroups'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  getIsPublicInput(): ElementFinder {
    return this.isPublicInput;
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setHelpInput(help: string): Promise<void> {
    await this.helpInput.sendKeys(help);
  }

  async getHelpInput(): Promise<string> {
    return await this.helpInput.getAttribute('value');
  }

  async createdBySelectLastOption(): Promise<void> {
    await this.createdBySelect.all(by.tagName('option')).last().click();
  }

  async createdBySelectOption(option: string): Promise<void> {
    await this.createdBySelect.sendKeys(option);
  }

  getCreatedBySelect(): ElementFinder {
    return this.createdBySelect;
  }

  async getCreatedBySelectedOption(): Promise<string> {
    return await this.createdBySelect.element(by.css('option:checked')).getText();
  }

  async visibleForOrganisationsSelectLastOption(): Promise<void> {
    await this.visibleForOrganisationsSelect.all(by.tagName('option')).last().click();
  }

  async visibleForOrganisationsSelectOption(option: string): Promise<void> {
    await this.visibleForOrganisationsSelect.sendKeys(option);
  }

  getVisibleForOrganisationsSelect(): ElementFinder {
    return this.visibleForOrganisationsSelect;
  }

  async getVisibleForOrganisationsSelectedOption(): Promise<string> {
    return await this.visibleForOrganisationsSelect.element(by.css('option:checked')).getText();
  }

  async visibleForGroupsSelectLastOption(): Promise<void> {
    await this.visibleForGroupsSelect.all(by.tagName('option')).last().click();
  }

  async visibleForGroupsSelectOption(option: string): Promise<void> {
    await this.visibleForGroupsSelect.sendKeys(option);
  }

  getVisibleForGroupsSelect(): ElementFinder {
    return this.visibleForGroupsSelect;
  }

  async getVisibleForGroupsSelectedOption(): Promise<string> {
    return await this.visibleForGroupsSelect.element(by.css('option:checked')).getText();
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

export class ActivityDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-activity-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-activity'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
