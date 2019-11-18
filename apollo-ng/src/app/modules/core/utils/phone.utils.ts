export class PhoneUtils {

  static phoneNumberToLinkURI(phoneNumber: string): string {
    if (!phoneNumber) {
      return '';
    }
    if (phoneNumber.charAt(0) !== '+' && phoneNumber.charAt(0) !== '0' ) {
      phoneNumber = '+420' + phoneNumber;
    }
    // remove whitespaces
    phoneNumber = phoneNumber.replace(/\s/g, '');
    phoneNumber = 'tel:' + phoneNumber;
    return phoneNumber;
  }

  static getSinglePhoneNumberFromEnvironment(env): string {
    if (!env || !env.phoneNumbers || env.phoneNumbers.length < 1) {
      return null;
    }
    return env.phoneNumbers[0];
  }


}
