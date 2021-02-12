import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { GroupComponentsPage, GroupDeleteDialog, GroupUpdatePage } from './group.page-object';

const expect = chai.expect;

describe('Group e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let groupComponentsPage: GroupComponentsPage;
  let groupUpdatePage: GroupUpdatePage;
  let groupDeleteDialog: GroupDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Groups', async () => {
    await navBarPage.goToEntity('group');
    groupComponentsPage = new GroupComponentsPage();
    await browser.wait(ec.visibilityOf(groupComponentsPage.title), 5000);
    expect(await groupComponentsPage.getTitle()).to.eq('Groups');
    await browser.wait(ec.or(ec.visibilityOf(groupComponentsPage.entities), ec.visibilityOf(groupComponentsPage.noResult)), 1000);
  });

  it('should load create Group page', async () => {
    await groupComponentsPage.clickOnCreateButton();
    groupUpdatePage = new GroupUpdatePage();
    expect(await groupUpdatePage.getPageTitle()).to.eq('Create or edit a Group');
    await groupUpdatePage.cancel();
  });

  it('should create and save Groups', async () => {
    const nbButtonsBeforeCreate = await groupComponentsPage.countDeleteButtons();

    await groupComponentsPage.clickOnCreateButton();

    await promise.all([
      groupUpdatePage.setNameInput('name'),
      groupUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      groupUpdatePage.setDescriptionInput('description'),
      groupUpdatePage.ownerSelectLastOption(),
    ]);

    expect(await groupUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await groupUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
    expect(await groupUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');

    await groupUpdatePage.save();
    expect(await groupUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await groupComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Group', async () => {
    const nbButtonsBeforeDelete = await groupComponentsPage.countDeleteButtons();
    await groupComponentsPage.clickOnLastDeleteButton();

    groupDeleteDialog = new GroupDeleteDialog();
    expect(await groupDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Group?');
    await groupDeleteDialog.clickOnConfirmButton();

    expect(await groupComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
