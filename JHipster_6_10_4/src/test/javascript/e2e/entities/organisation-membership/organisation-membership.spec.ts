import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  OrganisationMembershipComponentsPage,
  OrganisationMembershipDeleteDialog,
  OrganisationMembershipUpdatePage,
} from './organisation-membership.page-object';

const expect = chai.expect;

describe('OrganisationMembership e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let organisationMembershipComponentsPage: OrganisationMembershipComponentsPage;
  let organisationMembershipUpdatePage: OrganisationMembershipUpdatePage;
  let organisationMembershipDeleteDialog: OrganisationMembershipDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load OrganisationMemberships', async () => {
    await navBarPage.goToEntity('organisation-membership');
    organisationMembershipComponentsPage = new OrganisationMembershipComponentsPage();
    await browser.wait(ec.visibilityOf(organisationMembershipComponentsPage.title), 5000);
    expect(await organisationMembershipComponentsPage.getTitle()).to.eq('Organisation Memberships');
    await browser.wait(
      ec.or(ec.visibilityOf(organisationMembershipComponentsPage.entities), ec.visibilityOf(organisationMembershipComponentsPage.noResult)),
      1000
    );
  });

  it('should load create OrganisationMembership page', async () => {
    await organisationMembershipComponentsPage.clickOnCreateButton();
    organisationMembershipUpdatePage = new OrganisationMembershipUpdatePage();
    expect(await organisationMembershipUpdatePage.getPageTitle()).to.eq('Create or edit a Organisation Membership');
    await organisationMembershipUpdatePage.cancel();
  });

  it('should create and save OrganisationMemberships', async () => {
    const nbButtonsBeforeCreate = await organisationMembershipComponentsPage.countDeleteButtons();

    await organisationMembershipComponentsPage.clickOnCreateButton();

    await promise.all([
      organisationMembershipUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      organisationMembershipUpdatePage.userSelectLastOption(),
      // organisationMembershipUpdatePage.rolesSelectLastOption(),
      organisationMembershipUpdatePage.organisationSelectLastOption(),
    ]);

    expect(await organisationMembershipUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );

    await organisationMembershipUpdatePage.save();
    expect(await organisationMembershipUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await organisationMembershipComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last OrganisationMembership', async () => {
    const nbButtonsBeforeDelete = await organisationMembershipComponentsPage.countDeleteButtons();
    await organisationMembershipComponentsPage.clickOnLastDeleteButton();

    organisationMembershipDeleteDialog = new OrganisationMembershipDeleteDialog();
    expect(await organisationMembershipDeleteDialog.getDialogTitle()).to.eq(
      'Are you sure you want to delete this Organisation Membership?'
    );
    await organisationMembershipDeleteDialog.clickOnConfirmButton();

    expect(await organisationMembershipComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
