import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { UserPropertiesComponentsPage, UserPropertiesDeleteDialog, UserPropertiesUpdatePage } from './user-properties.page-object';

const expect = chai.expect;

describe('UserProperties e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let userPropertiesComponentsPage: UserPropertiesComponentsPage;
  let userPropertiesUpdatePage: UserPropertiesUpdatePage;
  let userPropertiesDeleteDialog: UserPropertiesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load UserProperties', async () => {
    await navBarPage.goToEntity('user-properties');
    userPropertiesComponentsPage = new UserPropertiesComponentsPage();
    await browser.wait(ec.visibilityOf(userPropertiesComponentsPage.title), 5000);
    expect(await userPropertiesComponentsPage.getTitle()).to.eq('User Properties');
    await browser.wait(
      ec.or(ec.visibilityOf(userPropertiesComponentsPage.entities), ec.visibilityOf(userPropertiesComponentsPage.noResult)),
      1000
    );
  });

  it('should load create UserProperties page', async () => {
    await userPropertiesComponentsPage.clickOnCreateButton();
    userPropertiesUpdatePage = new UserPropertiesUpdatePage();
    expect(await userPropertiesUpdatePage.getPageTitle()).to.eq('Create or edit a User Properties');
    await userPropertiesUpdatePage.cancel();
  });

  it('should create and save UserProperties', async () => {
    const nbButtonsBeforeCreate = await userPropertiesComponentsPage.countDeleteButtons();

    await userPropertiesComponentsPage.clickOnCreateButton();

    await promise.all([
      userPropertiesUpdatePage.setBirthDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      userPropertiesUpdatePage.setPhoneInput('phone'),
      userPropertiesUpdatePage.setNationalityInput('nationality'),
      userPropertiesUpdatePage.sexSelectLastOption(),
    ]);

    expect(await userPropertiesUpdatePage.getBirthDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected birthDate value to be equals to 2000-12-31'
    );
    expect(await userPropertiesUpdatePage.getPhoneInput()).to.eq('phone', 'Expected Phone value to be equals to phone');
    expect(await userPropertiesUpdatePage.getNationalityInput()).to.eq(
      'nationality',
      'Expected Nationality value to be equals to nationality'
    );

    await userPropertiesUpdatePage.save();
    expect(await userPropertiesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await userPropertiesComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last UserProperties', async () => {
    const nbButtonsBeforeDelete = await userPropertiesComponentsPage.countDeleteButtons();
    await userPropertiesComponentsPage.clickOnLastDeleteButton();

    userPropertiesDeleteDialog = new UserPropertiesDeleteDialog();
    expect(await userPropertiesDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this User Properties?');
    await userPropertiesDeleteDialog.clickOnConfirmButton();

    expect(await userPropertiesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
