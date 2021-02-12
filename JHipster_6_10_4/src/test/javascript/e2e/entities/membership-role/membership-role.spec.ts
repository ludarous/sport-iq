import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MembershipRoleComponentsPage, MembershipRoleDeleteDialog, MembershipRoleUpdatePage } from './membership-role.page-object';

const expect = chai.expect;

describe('MembershipRole e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let membershipRoleComponentsPage: MembershipRoleComponentsPage;
  let membershipRoleUpdatePage: MembershipRoleUpdatePage;
  let membershipRoleDeleteDialog: MembershipRoleDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load MembershipRoles', async () => {
    await navBarPage.goToEntity('membership-role');
    membershipRoleComponentsPage = new MembershipRoleComponentsPage();
    await browser.wait(ec.visibilityOf(membershipRoleComponentsPage.title), 5000);
    expect(await membershipRoleComponentsPage.getTitle()).to.eq('Membership Roles');
    await browser.wait(
      ec.or(ec.visibilityOf(membershipRoleComponentsPage.entities), ec.visibilityOf(membershipRoleComponentsPage.noResult)),
      1000
    );
  });

  it('should load create MembershipRole page', async () => {
    await membershipRoleComponentsPage.clickOnCreateButton();
    membershipRoleUpdatePage = new MembershipRoleUpdatePage();
    expect(await membershipRoleUpdatePage.getPageTitle()).to.eq('Create or edit a Membership Role');
    await membershipRoleUpdatePage.cancel();
  });

  it('should create and save MembershipRoles', async () => {
    const nbButtonsBeforeCreate = await membershipRoleComponentsPage.countDeleteButtons();

    await membershipRoleComponentsPage.clickOnCreateButton();

    await promise.all([membershipRoleUpdatePage.setNameInput('name')]);

    expect(await membershipRoleUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');

    await membershipRoleUpdatePage.save();
    expect(await membershipRoleUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await membershipRoleComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last MembershipRole', async () => {
    const nbButtonsBeforeDelete = await membershipRoleComponentsPage.countDeleteButtons();
    await membershipRoleComponentsPage.clickOnLastDeleteButton();

    membershipRoleDeleteDialog = new MembershipRoleDeleteDialog();
    expect(await membershipRoleDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Membership Role?');
    await membershipRoleDeleteDialog.clickOnConfirmButton();

    expect(await membershipRoleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
