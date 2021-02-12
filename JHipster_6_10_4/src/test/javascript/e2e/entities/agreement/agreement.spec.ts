import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AgreementComponentsPage, AgreementDeleteDialog, AgreementUpdatePage } from './agreement.page-object';

const expect = chai.expect;

describe('Agreement e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let agreementComponentsPage: AgreementComponentsPage;
  let agreementUpdatePage: AgreementUpdatePage;
  let agreementDeleteDialog: AgreementDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Agreements', async () => {
    await navBarPage.goToEntity('agreement');
    agreementComponentsPage = new AgreementComponentsPage();
    await browser.wait(ec.visibilityOf(agreementComponentsPage.title), 5000);
    expect(await agreementComponentsPage.getTitle()).to.eq('Agreements');
    await browser.wait(ec.or(ec.visibilityOf(agreementComponentsPage.entities), ec.visibilityOf(agreementComponentsPage.noResult)), 1000);
  });

  it('should load create Agreement page', async () => {
    await agreementComponentsPage.clickOnCreateButton();
    agreementUpdatePage = new AgreementUpdatePage();
    expect(await agreementUpdatePage.getPageTitle()).to.eq('Create or edit a Agreement');
    await agreementUpdatePage.cancel();
  });

  it('should create and save Agreements', async () => {
    const nbButtonsBeforeCreate = await agreementComponentsPage.countDeleteButtons();

    await agreementComponentsPage.clickOnCreateButton();

    await promise.all([]);

    const selectedTermsAgreement = agreementUpdatePage.getTermsAgreementInput();
    if (await selectedTermsAgreement.isSelected()) {
      await agreementUpdatePage.getTermsAgreementInput().click();
      expect(await agreementUpdatePage.getTermsAgreementInput().isSelected(), 'Expected termsAgreement not to be selected').to.be.false;
    } else {
      await agreementUpdatePage.getTermsAgreementInput().click();
      expect(await agreementUpdatePage.getTermsAgreementInput().isSelected(), 'Expected termsAgreement to be selected').to.be.true;
    }
    const selectedGdprAgreement = agreementUpdatePage.getGdprAgreementInput();
    if (await selectedGdprAgreement.isSelected()) {
      await agreementUpdatePage.getGdprAgreementInput().click();
      expect(await agreementUpdatePage.getGdprAgreementInput().isSelected(), 'Expected gdprAgreement not to be selected').to.be.false;
    } else {
      await agreementUpdatePage.getGdprAgreementInput().click();
      expect(await agreementUpdatePage.getGdprAgreementInput().isSelected(), 'Expected gdprAgreement to be selected').to.be.true;
    }
    const selectedPhotographyAgreement = agreementUpdatePage.getPhotographyAgreementInput();
    if (await selectedPhotographyAgreement.isSelected()) {
      await agreementUpdatePage.getPhotographyAgreementInput().click();
      expect(await agreementUpdatePage.getPhotographyAgreementInput().isSelected(), 'Expected photographyAgreement not to be selected').to
        .be.false;
    } else {
      await agreementUpdatePage.getPhotographyAgreementInput().click();
      expect(await agreementUpdatePage.getPhotographyAgreementInput().isSelected(), 'Expected photographyAgreement to be selected').to.be
        .true;
    }
    const selectedMarketingAgreement = agreementUpdatePage.getMarketingAgreementInput();
    if (await selectedMarketingAgreement.isSelected()) {
      await agreementUpdatePage.getMarketingAgreementInput().click();
      expect(await agreementUpdatePage.getMarketingAgreementInput().isSelected(), 'Expected marketingAgreement not to be selected').to.be
        .false;
    } else {
      await agreementUpdatePage.getMarketingAgreementInput().click();
      expect(await agreementUpdatePage.getMarketingAgreementInput().isSelected(), 'Expected marketingAgreement to be selected').to.be.true;
    }
    const selectedMedicalFitnessAgreement = agreementUpdatePage.getMedicalFitnessAgreementInput();
    if (await selectedMedicalFitnessAgreement.isSelected()) {
      await agreementUpdatePage.getMedicalFitnessAgreementInput().click();
      expect(
        await agreementUpdatePage.getMedicalFitnessAgreementInput().isSelected(),
        'Expected medicalFitnessAgreement not to be selected'
      ).to.be.false;
    } else {
      await agreementUpdatePage.getMedicalFitnessAgreementInput().click();
      expect(await agreementUpdatePage.getMedicalFitnessAgreementInput().isSelected(), 'Expected medicalFitnessAgreement to be selected').to
        .be.true;
    }

    await agreementUpdatePage.save();
    expect(await agreementUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await agreementComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Agreement', async () => {
    const nbButtonsBeforeDelete = await agreementComponentsPage.countDeleteButtons();
    await agreementComponentsPage.clickOnLastDeleteButton();

    agreementDeleteDialog = new AgreementDeleteDialog();
    expect(await agreementDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Agreement?');
    await agreementDeleteDialog.clickOnConfirmButton();

    expect(await agreementComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
