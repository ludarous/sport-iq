import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  LegalRepresentativeComponentsPage,
  LegalRepresentativeDeleteDialog,
  LegalRepresentativeUpdatePage,
} from './legal-representative.page-object';

const expect = chai.expect;

describe('LegalRepresentative e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let legalRepresentativeComponentsPage: LegalRepresentativeComponentsPage;
  let legalRepresentativeUpdatePage: LegalRepresentativeUpdatePage;
  let legalRepresentativeDeleteDialog: LegalRepresentativeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LegalRepresentatives', async () => {
    await navBarPage.goToEntity('legal-representative');
    legalRepresentativeComponentsPage = new LegalRepresentativeComponentsPage();
    await browser.wait(ec.visibilityOf(legalRepresentativeComponentsPage.title), 5000);
    expect(await legalRepresentativeComponentsPage.getTitle()).to.eq('Legal Representatives');
    await browser.wait(
      ec.or(ec.visibilityOf(legalRepresentativeComponentsPage.entities), ec.visibilityOf(legalRepresentativeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create LegalRepresentative page', async () => {
    await legalRepresentativeComponentsPage.clickOnCreateButton();
    legalRepresentativeUpdatePage = new LegalRepresentativeUpdatePage();
    expect(await legalRepresentativeUpdatePage.getPageTitle()).to.eq('Create or edit a Legal Representative');
    await legalRepresentativeUpdatePage.cancel();
  });

  it('should create and save LegalRepresentatives', async () => {
    const nbButtonsBeforeCreate = await legalRepresentativeComponentsPage.countDeleteButtons();

    await legalRepresentativeComponentsPage.clickOnCreateButton();

    await promise.all([
      legalRepresentativeUpdatePage.setFirstNameInput('firstName'),
      legalRepresentativeUpdatePage.setLastNameInput('lastName'),
      legalRepresentativeUpdatePage.setEmailInput('email'),
      legalRepresentativeUpdatePage.setPhoneInput('phone'),
    ]);

    expect(await legalRepresentativeUpdatePage.getFirstNameInput()).to.eq(
      'firstName',
      'Expected FirstName value to be equals to firstName'
    );
    expect(await legalRepresentativeUpdatePage.getLastNameInput()).to.eq('lastName', 'Expected LastName value to be equals to lastName');
    expect(await legalRepresentativeUpdatePage.getEmailInput()).to.eq('email', 'Expected Email value to be equals to email');
    expect(await legalRepresentativeUpdatePage.getPhoneInput()).to.eq('phone', 'Expected Phone value to be equals to phone');

    await legalRepresentativeUpdatePage.save();
    expect(await legalRepresentativeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await legalRepresentativeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last LegalRepresentative', async () => {
    const nbButtonsBeforeDelete = await legalRepresentativeComponentsPage.countDeleteButtons();
    await legalRepresentativeComponentsPage.clickOnLastDeleteButton();

    legalRepresentativeDeleteDialog = new LegalRepresentativeDeleteDialog();
    expect(await legalRepresentativeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Legal Representative?');
    await legalRepresentativeDeleteDialog.clickOnConfirmButton();

    expect(await legalRepresentativeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
