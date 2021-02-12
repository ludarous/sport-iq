import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SportComponentsPage, SportDeleteDialog, SportUpdatePage } from './sport.page-object';

const expect = chai.expect;

describe('Sport e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let sportComponentsPage: SportComponentsPage;
  let sportUpdatePage: SportUpdatePage;
  let sportDeleteDialog: SportDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Sports', async () => {
    await navBarPage.goToEntity('sport');
    sportComponentsPage = new SportComponentsPage();
    await browser.wait(ec.visibilityOf(sportComponentsPage.title), 5000);
    expect(await sportComponentsPage.getTitle()).to.eq('Sports');
    await browser.wait(ec.or(ec.visibilityOf(sportComponentsPage.entities), ec.visibilityOf(sportComponentsPage.noResult)), 1000);
  });

  it('should load create Sport page', async () => {
    await sportComponentsPage.clickOnCreateButton();
    sportUpdatePage = new SportUpdatePage();
    expect(await sportUpdatePage.getPageTitle()).to.eq('Create or edit a Sport');
    await sportUpdatePage.cancel();
  });

  it('should create and save Sports', async () => {
    const nbButtonsBeforeCreate = await sportComponentsPage.countDeleteButtons();

    await sportComponentsPage.clickOnCreateButton();

    await promise.all([sportUpdatePage.setNameInput('name')]);

    expect(await sportUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');

    await sportUpdatePage.save();
    expect(await sportUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await sportComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Sport', async () => {
    const nbButtonsBeforeDelete = await sportComponentsPage.countDeleteButtons();
    await sportComponentsPage.clickOnLastDeleteButton();

    sportDeleteDialog = new SportDeleteDialog();
    expect(await sportDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Sport?');
    await sportDeleteDialog.clickOnConfirmButton();

    expect(await sportComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
