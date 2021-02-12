import { element, by, ElementFinder } from 'protractor';

export class AgreementComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-agreement div table .btn-danger'));
  title = element.all(by.css('jhi-agreement div h2#page-heading span')).first();
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

export class AgreementUpdatePage {
  pageTitle = element(by.id('jhi-agreement-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  termsAgreementInput = element(by.id('field_termsAgreement'));
  gdprAgreementInput = element(by.id('field_gdprAgreement'));
  photographyAgreementInput = element(by.id('field_photographyAgreement'));
  marketingAgreementInput = element(by.id('field_marketingAgreement'));
  medicalFitnessAgreementInput = element(by.id('field_medicalFitnessAgreement'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  getTermsAgreementInput(): ElementFinder {
    return this.termsAgreementInput;
  }

  getGdprAgreementInput(): ElementFinder {
    return this.gdprAgreementInput;
  }

  getPhotographyAgreementInput(): ElementFinder {
    return this.photographyAgreementInput;
  }

  getMarketingAgreementInput(): ElementFinder {
    return this.marketingAgreementInput;
  }

  getMedicalFitnessAgreementInput(): ElementFinder {
    return this.medicalFitnessAgreementInput;
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

export class AgreementDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-agreement-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-agreement'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
