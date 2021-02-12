import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  BodyCharacteristicsComponentsPage,
  BodyCharacteristicsDeleteDialog,
  BodyCharacteristicsUpdatePage,
} from './body-characteristics.page-object';

const expect = chai.expect;

describe('BodyCharacteristics e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let bodyCharacteristicsComponentsPage: BodyCharacteristicsComponentsPage;
  let bodyCharacteristicsUpdatePage: BodyCharacteristicsUpdatePage;
  let bodyCharacteristicsDeleteDialog: BodyCharacteristicsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load BodyCharacteristics', async () => {
    await navBarPage.goToEntity('body-characteristics');
    bodyCharacteristicsComponentsPage = new BodyCharacteristicsComponentsPage();
    await browser.wait(ec.visibilityOf(bodyCharacteristicsComponentsPage.title), 5000);
    expect(await bodyCharacteristicsComponentsPage.getTitle()).to.eq('Body Characteristics');
    await browser.wait(
      ec.or(ec.visibilityOf(bodyCharacteristicsComponentsPage.entities), ec.visibilityOf(bodyCharacteristicsComponentsPage.noResult)),
      1000
    );
  });

  it('should load create BodyCharacteristics page', async () => {
    await bodyCharacteristicsComponentsPage.clickOnCreateButton();
    bodyCharacteristicsUpdatePage = new BodyCharacteristicsUpdatePage();
    expect(await bodyCharacteristicsUpdatePage.getPageTitle()).to.eq('Create or edit a Body Characteristics');
    await bodyCharacteristicsUpdatePage.cancel();
  });

  it('should create and save BodyCharacteristics', async () => {
    const nbButtonsBeforeCreate = await bodyCharacteristicsComponentsPage.countDeleteButtons();

    await bodyCharacteristicsComponentsPage.clickOnCreateButton();

    await promise.all([
      bodyCharacteristicsUpdatePage.setHeightInput('5'),
      bodyCharacteristicsUpdatePage.setWeightInput('5'),
      bodyCharacteristicsUpdatePage.setDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      bodyCharacteristicsUpdatePage.heightUnitSelectLastOption(),
      bodyCharacteristicsUpdatePage.widthUnitSelectLastOption(),
    ]);

    expect(await bodyCharacteristicsUpdatePage.getHeightInput()).to.eq('5', 'Expected height value to be equals to 5');
    expect(await bodyCharacteristicsUpdatePage.getWeightInput()).to.eq('5', 'Expected weight value to be equals to 5');
    expect(await bodyCharacteristicsUpdatePage.getDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected date value to be equals to 2000-12-31'
    );

    await bodyCharacteristicsUpdatePage.save();
    expect(await bodyCharacteristicsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await bodyCharacteristicsComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last BodyCharacteristics', async () => {
    const nbButtonsBeforeDelete = await bodyCharacteristicsComponentsPage.countDeleteButtons();
    await bodyCharacteristicsComponentsPage.clickOnLastDeleteButton();

    bodyCharacteristicsDeleteDialog = new BodyCharacteristicsDeleteDialog();
    expect(await bodyCharacteristicsDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Body Characteristics?');
    await bodyCharacteristicsDeleteDialog.clickOnConfirmButton();

    expect(await bodyCharacteristicsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
