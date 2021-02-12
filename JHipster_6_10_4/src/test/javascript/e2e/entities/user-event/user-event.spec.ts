import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  UserEventComponentsPage,
  /* UserEventDeleteDialog, */
  UserEventUpdatePage,
} from './user-event.page-object';

const expect = chai.expect;

describe('UserEvent e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let userEventComponentsPage: UserEventComponentsPage;
  let userEventUpdatePage: UserEventUpdatePage;
  /* let userEventDeleteDialog: UserEventDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load UserEvents', async () => {
    await navBarPage.goToEntity('user-event');
    userEventComponentsPage = new UserEventComponentsPage();
    await browser.wait(ec.visibilityOf(userEventComponentsPage.title), 5000);
    expect(await userEventComponentsPage.getTitle()).to.eq('User Events');
    await browser.wait(ec.or(ec.visibilityOf(userEventComponentsPage.entities), ec.visibilityOf(userEventComponentsPage.noResult)), 1000);
  });

  it('should load create UserEvent page', async () => {
    await userEventComponentsPage.clickOnCreateButton();
    userEventUpdatePage = new UserEventUpdatePage();
    expect(await userEventUpdatePage.getPageTitle()).to.eq('Create or edit a User Event');
    await userEventUpdatePage.cancel();
  });

  /* it('should create and save UserEvents', async () => {
        const nbButtonsBeforeCreate = await userEventComponentsPage.countDeleteButtons();

        await userEventComponentsPage.clickOnCreateButton();

        await promise.all([
            userEventUpdatePage.setNoteInput('note'),
            userEventUpdatePage.setRegistrationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            userEventUpdatePage.userSelectLastOption(),
            userEventUpdatePage.eventSelectLastOption(),
        ]);

        expect(await userEventUpdatePage.getNoteInput()).to.eq('note', 'Expected Note value to be equals to note');
        expect(await userEventUpdatePage.getRegistrationDateInput()).to.contain('2001-01-01T02:30', 'Expected registrationDate value to be equals to 2000-12-31');

        await userEventUpdatePage.save();
        expect(await userEventUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await userEventComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last UserEvent', async () => {
        const nbButtonsBeforeDelete = await userEventComponentsPage.countDeleteButtons();
        await userEventComponentsPage.clickOnLastDeleteButton();

        userEventDeleteDialog = new UserEventDeleteDialog();
        expect(await userEventDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this User Event?');
        await userEventDeleteDialog.clickOnConfirmButton();

        expect(await userEventComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
