import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OrganisationComponentsPage, OrganisationDeleteDialog, OrganisationUpdatePage } from './organisation.page-object';

const expect = chai.expect;

describe('Organisation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let organisationComponentsPage: OrganisationComponentsPage;
  let organisationUpdatePage: OrganisationUpdatePage;
  let organisationDeleteDialog: OrganisationDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Organisations', async () => {
    await navBarPage.goToEntity('organisation');
    organisationComponentsPage = new OrganisationComponentsPage();
    await browser.wait(ec.visibilityOf(organisationComponentsPage.title), 5000);
    expect(await organisationComponentsPage.getTitle()).to.eq('Organisations');
    await browser.wait(
      ec.or(ec.visibilityOf(organisationComponentsPage.entities), ec.visibilityOf(organisationComponentsPage.noResult)),
      1000
    );
  });

  it('should load create Organisation page', async () => {
    await organisationComponentsPage.clickOnCreateButton();
    organisationUpdatePage = new OrganisationUpdatePage();
    expect(await organisationUpdatePage.getPageTitle()).to.eq('Create or edit a Organisation');
    await organisationUpdatePage.cancel();
  });

  it('should create and save Organisations', async () => {
    const nbButtonsBeforeCreate = await organisationComponentsPage.countDeleteButtons();

    await organisationComponentsPage.clickOnCreateButton();

    await promise.all([
      organisationUpdatePage.setNameInput('name'),
      organisationUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      organisationUpdatePage.setDescriptionInput('description'),
      organisationUpdatePage.ownerSelectLastOption(),
    ]);

    expect(await organisationUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await organisationUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await organisationUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await organisationUpdatePage.save();
    expect(await organisationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await organisationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Organisation', async () => {
    const nbButtonsBeforeDelete = await organisationComponentsPage.countDeleteButtons();
    await organisationComponentsPage.clickOnLastDeleteButton();

    organisationDeleteDialog = new OrganisationDeleteDialog();
    expect(await organisationDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Organisation?');
    await organisationDeleteDialog.clickOnConfirmButton();

    expect(await organisationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
