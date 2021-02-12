import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ActivityResultComponentsPage, ActivityResultDeleteDialog, ActivityResultUpdatePage } from './activity-result.page-object';

const expect = chai.expect;

describe('ActivityResult e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let activityResultComponentsPage: ActivityResultComponentsPage;
  let activityResultUpdatePage: ActivityResultUpdatePage;
  let activityResultDeleteDialog: ActivityResultDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ActivityResults', async () => {
    await navBarPage.goToEntity('activity-result');
    activityResultComponentsPage = new ActivityResultComponentsPage();
    await browser.wait(ec.visibilityOf(activityResultComponentsPage.title), 5000);
    expect(await activityResultComponentsPage.getTitle()).to.eq('Activity Results');
    await browser.wait(
      ec.or(ec.visibilityOf(activityResultComponentsPage.entities), ec.visibilityOf(activityResultComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ActivityResult page', async () => {
    await activityResultComponentsPage.clickOnCreateButton();
    activityResultUpdatePage = new ActivityResultUpdatePage();
    expect(await activityResultUpdatePage.getPageTitle()).to.eq('Create or edit a Activity Result');
    await activityResultUpdatePage.cancel();
  });

  it('should create and save ActivityResults', async () => {
    const nbButtonsBeforeCreate = await activityResultComponentsPage.countDeleteButtons();

    await activityResultComponentsPage.clickOnCreateButton();

    await promise.all([
      activityResultUpdatePage.setNameInput('name'),
      activityResultUpdatePage.resultTypeSelectLastOption(),
      activityResultUpdatePage.setRatingWeightInput('5'),
      activityResultUpdatePage.setOrderInput('5'),
      activityResultUpdatePage.setIrvBestInput('5'),
      activityResultUpdatePage.setIrvWorstInput('5'),
      activityResultUpdatePage.resultUnitSelectLastOption(),
      activityResultUpdatePage.activitySelectLastOption(),
    ]);

    expect(await activityResultUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await activityResultUpdatePage.getRatingWeightInput()).to.eq('5', 'Expected ratingWeight value to be equals to 5');
    const selectedMainResult = activityResultUpdatePage.getMainResultInput();
    if (await selectedMainResult.isSelected()) {
      await activityResultUpdatePage.getMainResultInput().click();
      expect(await activityResultUpdatePage.getMainResultInput().isSelected(), 'Expected mainResult not to be selected').to.be.false;
    } else {
      await activityResultUpdatePage.getMainResultInput().click();
      expect(await activityResultUpdatePage.getMainResultInput().isSelected(), 'Expected mainResult to be selected').to.be.true;
    }
    expect(await activityResultUpdatePage.getOrderInput()).to.eq('5', 'Expected order value to be equals to 5');
    expect(await activityResultUpdatePage.getIrvBestInput()).to.eq('5', 'Expected irvBest value to be equals to 5');
    expect(await activityResultUpdatePage.getIrvWorstInput()).to.eq('5', 'Expected irvWorst value to be equals to 5');

    await activityResultUpdatePage.save();
    expect(await activityResultUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await activityResultComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ActivityResult', async () => {
    const nbButtonsBeforeDelete = await activityResultComponentsPage.countDeleteButtons();
    await activityResultComponentsPage.clickOnLastDeleteButton();

    activityResultDeleteDialog = new ActivityResultDeleteDialog();
    expect(await activityResultDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Activity Result?');
    await activityResultDeleteDialog.clickOnConfirmButton();

    expect(await activityResultComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
