import { element, by, ElementFinder } from 'protractor';

export class OrganisationMembershipComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-organisation-membership div table .btn-danger'));
  title = element.all(by.css('jhi-organisation-membership div h2#page-heading span')).first();
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

export class OrganisationMembershipUpdatePage {
  pageTitle = element(by.id('jhi-organisation-membership-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  createdInput = element(by.id('field_created'));

  userSelect = element(by.id('field_user'));
  rolesSelect = element(by.id('field_roles'));
  organisationSelect = element(by.id('field_organisation'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setCreatedInput(created: string): Promise<void> {
    await this.createdInput.sendKeys(created);
  }

  async getCreatedInput(): Promise<string> {
    return await this.createdInput.getAttribute('value');
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

  async rolesSelectLastOption(): Promise<void> {
    await this.rolesSelect.all(by.tagName('option')).last().click();
  }

  async rolesSelectOption(option: string): Promise<void> {
    await this.rolesSelect.sendKeys(option);
  }

  getRolesSelect(): ElementFinder {
    return this.rolesSelect;
  }

  async getRolesSelectedOption(): Promise<string> {
    return await this.rolesSelect.element(by.css('option:checked')).getText();
  }

  async organisationSelectLastOption(): Promise<void> {
    await this.organisationSelect.all(by.tagName('option')).last().click();
  }

  async organisationSelectOption(option: string): Promise<void> {
    await this.organisationSelect.sendKeys(option);
  }

  getOrganisationSelect(): ElementFinder {
    return this.organisationSelect;
  }

  async getOrganisationSelectedOption(): Promise<string> {
    return await this.organisationSelect.element(by.css('option:checked')).getText();
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

export class OrganisationMembershipDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-organisationMembership-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-organisationMembership'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
