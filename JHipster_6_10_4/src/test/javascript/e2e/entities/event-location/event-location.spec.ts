import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EventLocationComponentsPage, EventLocationDeleteDialog, EventLocationUpdatePage } from './event-location.page-object';

const expect = chai.expect;

describe('EventLocation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let eventLocationComponentsPage: EventLocationComponentsPage;
  let eventLocationUpdatePage: EventLocationUpdatePage;
  let eventLocationDeleteDialog: EventLocationDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EventLocations', async () => {
    await navBarPage.goToEntity('event-location');
    eventLocationComponentsPage = new EventLocationComponentsPage();
    await browser.wait(ec.visibilityOf(eventLocationComponentsPage.title), 5000);
    expect(await eventLocationComponentsPage.getTitle()).to.eq('Event Locations');
    await browser.wait(
      ec.or(ec.visibilityOf(eventLocationComponentsPage.entities), ec.visibilityOf(eventLocationComponentsPage.noResult)),
      1000
    );
  });

  it('should load create EventLocation page', async () => {
    await eventLocationComponentsPage.clickOnCreateButton();
    eventLocationUpdatePage = new EventLocationUpdatePage();
    expect(await eventLocationUpdatePage.getPageTitle()).to.eq('Create or edit a Event Location');
    await eventLocationUpdatePage.cancel();
  });

  it('should create and save EventLocations', async () => {
    const nbButtonsBeforeCreate = await eventLocationComponentsPage.countDeleteButtons();

    await eventLocationComponentsPage.clickOnCreateButton();

    await promise.all([
      eventLocationUpdatePage.setNameInput('name'),
      eventLocationUpdatePage.setStateInput('state'),
      eventLocationUpdatePage.setCityInput('city'),
      eventLocationUpdatePage.setStreetInput('street'),
      eventLocationUpdatePage.setStreetNumberInput('streetNumber'),
      eventLocationUpdatePage.setLatitudeInput('5'),
      eventLocationUpdatePage.setLongitudeInput('5'),
      eventLocationUpdatePage.setCapacityInput('5'),
      eventLocationUpdatePage.setMapLinkInput('mapLink'),
    ]);

    expect(await eventLocationUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await eventLocationUpdatePage.getStateInput()).to.eq('state', 'Expected State value to be equals to state');
    expect(await eventLocationUpdatePage.getCityInput()).to.eq('city', 'Expected City value to be equals to city');
    expect(await eventLocationUpdatePage.getStreetInput()).to.eq('street', 'Expected Street value to be equals to street');
    expect(await eventLocationUpdatePage.getStreetNumberInput()).to.eq(
      'streetNumber',
      'Expected StreetNumber value to be equals to streetNumber'
    );
    expect(await eventLocationUpdatePage.getLatitudeInput()).to.eq('5', 'Expected latitude value to be equals to 5');
    expect(await eventLocationUpdatePage.getLongitudeInput()).to.eq('5', 'Expected longitude value to be equals to 5');
    expect(await eventLocationUpdatePage.getCapacityInput()).to.eq('5', 'Expected capacity value to be equals to 5');
    expect(await eventLocationUpdatePage.getMapLinkInput()).to.eq('mapLink', 'Expected MapLink value to be equals to mapLink');

    await eventLocationUpdatePage.save();
    expect(await eventLocationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await eventLocationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last EventLocation', async () => {
    const nbButtonsBeforeDelete = await eventLocationComponentsPage.countDeleteButtons();
    await eventLocationComponentsPage.clickOnLastDeleteButton();

    eventLocationDeleteDialog = new EventLocationDeleteDialog();
    expect(await eventLocationDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Event Location?');
    await eventLocationDeleteDialog.clickOnConfirmButton();

    expect(await eventLocationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
