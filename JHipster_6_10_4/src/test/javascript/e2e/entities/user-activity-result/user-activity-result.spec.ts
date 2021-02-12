import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  UserActivityResultComponentsPage,
  /* UserActivityResultDeleteDialog, */
  UserActivityResultUpdatePage,
} from './user-activity-result.page-object';

const expect = chai.expect;

describe('UserActivityResult e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let userActivityResultComponentsPage: UserActivityResultComponentsPage;
  let userActivityResultUpdatePage: UserActivityResultUpdatePage;
  /* let userActivityResultDeleteDialog: UserActivityResultDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load UserActivityResults', async () => {
    await navBarPage.goToEntity('user-activity-result');
    userActivityResultComponentsPage = new UserActivityResultComponentsPage();
    await browser.wait(ec.visibilityOf(userActivityResultComponentsPage.title), 5000);
    expect(await userActivityResultComponentsPage.getTitle()).to.eq('User Activity Results');
    await browser.wait(
      ec.or(ec.visibilityOf(userActivityResultComponentsPage.entities), ec.visibilityOf(userActivityResultComponentsPage.noResult)),
      1000
    );
  });

  it('should load create UserActivityResult page', async () => {
    await userActivityResultComponentsPage.clickOnCreateButton();
    userActivityResultUpdatePage = new UserActivityResultUpdatePage();
    expect(await userActivityResultUpdatePage.getPageTitle()).to.eq('Create or edit a User Activity Result');
    await userActivityResultUpdatePage.cancel();
  });

  /* it('should create and save UserActivityResults', async () => {
        const nbButtonsBeforeCreate = await userActivityResultComponentsPage.countDeleteButtons();

        await userActivityResultComponentsPage.clickOnCreateButton();

        await promise.all([
            userActivityResultUpdatePage.setValueInput('5'),
            userActivityResultUpdatePage.setCompareValueInput('5'),
            userActivityResultUpdatePage.activityResultSelectLastOption(),
            userActivityResultUpdatePage.userActivitySelectLastOption(),
        ]);

        expect(await userActivityResultUpdatePage.getValueInput()).to.eq('5', 'Expected value value to be equals to 5');
        expect(await userActivityResultUpdatePage.getCompareValueInput()).to.eq('5', 'Expected compareValue value to be equals to 5');

        await userActivityResultUpdatePage.save();
        expect(await userActivityResultUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await userActivityResultComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last UserActivityResult', async () => {
        const nbButtonsBeforeDelete = await userActivityResultComponentsPage.countDeleteButtons();
        await userActivityResultComponentsPage.clickOnLastDeleteButton();

        userActivityResultDeleteDialog = new UserActivityResultDeleteDialog();
        expect(await userActivityResultDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this User Activity Result?');
        await userActivityResultDeleteDialog.clickOnConfirmButton();

        expect(await userActivityResultComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
