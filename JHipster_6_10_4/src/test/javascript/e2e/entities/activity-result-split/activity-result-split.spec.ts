import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ActivityResultSplitComponentsPage,
  ActivityResultSplitDeleteDialog,
  ActivityResultSplitUpdatePage,
} from './activity-result-split.page-object';

const expect = chai.expect;

describe('ActivityResultSplit e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let activityResultSplitComponentsPage: ActivityResultSplitComponentsPage;
  let activityResultSplitUpdatePage: ActivityResultSplitUpdatePage;
  let activityResultSplitDeleteDialog: ActivityResultSplitDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ActivityResultSplits', async () => {
    await navBarPage.goToEntity('activity-result-split');
    activityResultSplitComponentsPage = new ActivityResultSplitComponentsPage();
    await browser.wait(ec.visibilityOf(activityResultSplitComponentsPage.title), 5000);
    expect(await activityResultSplitComponentsPage.getTitle()).to.eq('Activity Result Splits');
    await browser.wait(
      ec.or(ec.visibilityOf(activityResultSplitComponentsPage.entities), ec.visibilityOf(activityResultSplitComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ActivityResultSplit page', async () => {
    await activityResultSplitComponentsPage.clickOnCreateButton();
    activityResultSplitUpdatePage = new ActivityResultSplitUpdatePage();
    expect(await activityResultSplitUpdatePage.getPageTitle()).to.eq('Create or edit a Activity Result Split');
    await activityResultSplitUpdatePage.cancel();
  });

  it('should create and save ActivityResultSplits', async () => {
    const nbButtonsBeforeCreate = await activityResultSplitComponentsPage.countDeleteButtons();

    await activityResultSplitComponentsPage.clickOnCreateButton();

    await promise.all([
      activityResultSplitUpdatePage.setSplitValueInput('5'),
      activityResultSplitUpdatePage.splitUnitSelectLastOption(),
      activityResultSplitUpdatePage.activityResultSelectLastOption(),
    ]);

    expect(await activityResultSplitUpdatePage.getSplitValueInput()).to.eq('5', 'Expected splitValue value to be equals to 5');

    await activityResultSplitUpdatePage.save();
    expect(await activityResultSplitUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await activityResultSplitComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ActivityResultSplit', async () => {
    const nbButtonsBeforeDelete = await activityResultSplitComponentsPage.countDeleteButtons();
    await activityResultSplitComponentsPage.clickOnLastDeleteButton();

    activityResultSplitDeleteDialog = new ActivityResultSplitDeleteDialog();
    expect(await activityResultSplitDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Activity Result Split?');
    await activityResultSplitDeleteDialog.clickOnConfirmButton();

    expect(await activityResultSplitComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
