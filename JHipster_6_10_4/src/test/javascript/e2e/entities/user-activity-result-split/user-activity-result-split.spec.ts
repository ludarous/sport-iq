import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  UserActivityResultSplitComponentsPage,
  /* UserActivityResultSplitDeleteDialog, */
  UserActivityResultSplitUpdatePage,
} from './user-activity-result-split.page-object';

const expect = chai.expect;

describe('UserActivityResultSplit e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let userActivityResultSplitComponentsPage: UserActivityResultSplitComponentsPage;
  let userActivityResultSplitUpdatePage: UserActivityResultSplitUpdatePage;
  /* let userActivityResultSplitDeleteDialog: UserActivityResultSplitDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load UserActivityResultSplits', async () => {
    await navBarPage.goToEntity('user-activity-result-split');
    userActivityResultSplitComponentsPage = new UserActivityResultSplitComponentsPage();
    await browser.wait(ec.visibilityOf(userActivityResultSplitComponentsPage.title), 5000);
    expect(await userActivityResultSplitComponentsPage.getTitle()).to.eq('User Activity Result Splits');
    await browser.wait(
      ec.or(
        ec.visibilityOf(userActivityResultSplitComponentsPage.entities),
        ec.visibilityOf(userActivityResultSplitComponentsPage.noResult)
      ),
      1000
    );
  });

  it('should load create UserActivityResultSplit page', async () => {
    await userActivityResultSplitComponentsPage.clickOnCreateButton();
    userActivityResultSplitUpdatePage = new UserActivityResultSplitUpdatePage();
    expect(await userActivityResultSplitUpdatePage.getPageTitle()).to.eq('Create or edit a User Activity Result Split');
    await userActivityResultSplitUpdatePage.cancel();
  });

  /* it('should create and save UserActivityResultSplits', async () => {
        const nbButtonsBeforeCreate = await userActivityResultSplitComponentsPage.countDeleteButtons();

        await userActivityResultSplitComponentsPage.clickOnCreateButton();

        await promise.all([
            userActivityResultSplitUpdatePage.setValueInput('5'),
            userActivityResultSplitUpdatePage.setCompareValueInput('5'),
            userActivityResultSplitUpdatePage.activityResultSplitSelectLastOption(),
            userActivityResultSplitUpdatePage.userActivityResultSelectLastOption(),
        ]);

        expect(await userActivityResultSplitUpdatePage.getValueInput()).to.eq('5', 'Expected value value to be equals to 5');
        expect(await userActivityResultSplitUpdatePage.getCompareValueInput()).to.eq('5', 'Expected compareValue value to be equals to 5');

        await userActivityResultSplitUpdatePage.save();
        expect(await userActivityResultSplitUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await userActivityResultSplitComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last UserActivityResultSplit', async () => {
        const nbButtonsBeforeDelete = await userActivityResultSplitComponentsPage.countDeleteButtons();
        await userActivityResultSplitComponentsPage.clickOnLastDeleteButton();

        userActivityResultSplitDeleteDialog = new UserActivityResultSplitDeleteDialog();
        expect(await userActivityResultSplitDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this User Activity Result Split?');
        await userActivityResultSplitDeleteDialog.clickOnConfirmButton();

        expect(await userActivityResultSplitComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
