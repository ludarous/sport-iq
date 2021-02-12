import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { GroupMembershipComponentsPage, GroupMembershipDeleteDialog, GroupMembershipUpdatePage } from './group-membership.page-object';

const expect = chai.expect;

describe('GroupMembership e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let groupMembershipComponentsPage: GroupMembershipComponentsPage;
  let groupMembershipUpdatePage: GroupMembershipUpdatePage;
  let groupMembershipDeleteDialog: GroupMembershipDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load GroupMemberships', async () => {
    await navBarPage.goToEntity('group-membership');
    groupMembershipComponentsPage = new GroupMembershipComponentsPage();
    await browser.wait(ec.visibilityOf(groupMembershipComponentsPage.title), 5000);
    expect(await groupMembershipComponentsPage.getTitle()).to.eq('Group Memberships');
    await browser.wait(
      ec.or(ec.visibilityOf(groupMembershipComponentsPage.entities), ec.visibilityOf(groupMembershipComponentsPage.noResult)),
      1000
    );
  });

  it('should load create GroupMembership page', async () => {
    await groupMembershipComponentsPage.clickOnCreateButton();
    groupMembershipUpdatePage = new GroupMembershipUpdatePage();
    expect(await groupMembershipUpdatePage.getPageTitle()).to.eq('Create or edit a Group Membership');
    await groupMembershipUpdatePage.cancel();
  });

  it('should create and save GroupMemberships', async () => {
    const nbButtonsBeforeCreate = await groupMembershipComponentsPage.countDeleteButtons();

    await groupMembershipComponentsPage.clickOnCreateButton();

    await promise.all([
      groupMembershipUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      groupMembershipUpdatePage.userSelectLastOption(),
      // groupMembershipUpdatePage.rolesSelectLastOption(),
      groupMembershipUpdatePage.groupSelectLastOption(),
    ]);

    expect(await groupMembershipUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );

    await groupMembershipUpdatePage.save();
    expect(await groupMembershipUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await groupMembershipComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last GroupMembership', async () => {
    const nbButtonsBeforeDelete = await groupMembershipComponentsPage.countDeleteButtons();
    await groupMembershipComponentsPage.clickOnLastDeleteButton();

    groupMembershipDeleteDialog = new GroupMembershipDeleteDialog();
    expect(await groupMembershipDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Group Membership?');
    await groupMembershipDeleteDialog.clickOnConfirmButton();

    expect(await groupMembershipComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
