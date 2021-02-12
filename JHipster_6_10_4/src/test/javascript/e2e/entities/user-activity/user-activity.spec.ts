import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  UserActivityComponentsPage,
  /* UserActivityDeleteDialog, */
  UserActivityUpdatePage,
} from './user-activity.page-object';

const expect = chai.expect;

describe('UserActivity e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let userActivityComponentsPage: UserActivityComponentsPage;
  let userActivityUpdatePage: UserActivityUpdatePage;
  /* let userActivityDeleteDialog: UserActivityDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load UserActivities', async () => {
    await navBarPage.goToEntity('user-activity');
    userActivityComponentsPage = new UserActivityComponentsPage();
    await browser.wait(ec.visibilityOf(userActivityComponentsPage.title), 5000);
    expect(await userActivityComponentsPage.getTitle()).to.eq('User Activities');
    await browser.wait(
      ec.or(ec.visibilityOf(userActivityComponentsPage.entities), ec.visibilityOf(userActivityComponentsPage.noResult)),
      1000
    );
  });

  it('should load create UserActivity page', async () => {
    await userActivityComponentsPage.clickOnCreateButton();
    userActivityUpdatePage = new UserActivityUpdatePage();
    expect(await userActivityUpdatePage.getPageTitle()).to.eq('Create or edit a User Activity');
    await userActivityUpdatePage.cancel();
  });

  /* it('should create and save UserActivities', async () => {
        const nbButtonsBeforeCreate = await userActivityComponentsPage.countDeleteButtons();

        await userActivityComponentsPage.clickOnCreateButton();

        await promise.all([
            userActivityUpdatePage.setNoteInput('note'),
            userActivityUpdatePage.setDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            userActivityUpdatePage.activitySelectLastOption(),
            userActivityUpdatePage.userEventSelectLastOption(),
        ]);

        expect(await userActivityUpdatePage.getNoteInput()).to.eq('note', 'Expected Note value to be equals to note');
        expect(await userActivityUpdatePage.getDateInput()).to.contain('2001-01-01T02:30', 'Expected date value to be equals to 2000-12-31');

        await userActivityUpdatePage.save();
        expect(await userActivityUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await userActivityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last UserActivity', async () => {
        const nbButtonsBeforeDelete = await userActivityComponentsPage.countDeleteButtons();
        await userActivityComponentsPage.clickOnLastDeleteButton();

        userActivityDeleteDialog = new UserActivityDeleteDialog();
        expect(await userActivityDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this User Activity?');
        await userActivityDeleteDialog.clickOnConfirmButton();

        expect(await userActivityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
