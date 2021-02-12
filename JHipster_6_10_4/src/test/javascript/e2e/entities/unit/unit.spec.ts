import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { UnitComponentsPage, UnitDeleteDialog, UnitUpdatePage } from './unit.page-object';

const expect = chai.expect;

describe('Unit e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let unitComponentsPage: UnitComponentsPage;
  let unitUpdatePage: UnitUpdatePage;
  let unitDeleteDialog: UnitDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Units', async () => {
    await navBarPage.goToEntity('unit');
    unitComponentsPage = new UnitComponentsPage();
    await browser.wait(ec.visibilityOf(unitComponentsPage.title), 5000);
    expect(await unitComponentsPage.getTitle()).to.eq('Units');
    await browser.wait(ec.or(ec.visibilityOf(unitComponentsPage.entities), ec.visibilityOf(unitComponentsPage.noResult)), 1000);
  });

  it('should load create Unit page', async () => {
    await unitComponentsPage.clickOnCreateButton();
    unitUpdatePage = new UnitUpdatePage();
    expect(await unitUpdatePage.getPageTitle()).to.eq('Create or edit a Unit');
    await unitUpdatePage.cancel();
  });

  it('should create and save Units', async () => {
    const nbButtonsBeforeCreate = await unitComponentsPage.countDeleteButtons();

    await unitComponentsPage.clickOnCreateButton();

    await promise.all([unitUpdatePage.setNameInput('name'), unitUpdatePage.setAbbreviationInput('abbreviation')]);

    expect(await unitUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await unitUpdatePage.getAbbreviationInput()).to.eq('abbreviation', 'Expected Abbreviation value to be equals to abbreviation');

    await unitUpdatePage.save();
    expect(await unitUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await unitComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Unit', async () => {
    const nbButtonsBeforeDelete = await unitComponentsPage.countDeleteButtons();
    await unitComponentsPage.clickOnLastDeleteButton();

    unitDeleteDialog = new UnitDeleteDialog();
    expect(await unitDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Unit?');
    await unitDeleteDialog.clickOnConfirmButton();

    expect(await unitComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
