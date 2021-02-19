export interface IAgreement {
  id?: number;
  termsAgreement?: boolean;
  gdprAgreement?: boolean;
  photographyAgreement?: boolean;
  marketingAgreement?: boolean;
  medicalFitnessAgreement?: boolean;
}

export class Agreement implements IAgreement {
  constructor(
    public id?: number,
    public termsAgreement?: boolean,
    public gdprAgreement?: boolean,
    public photographyAgreement?: boolean,
    public marketingAgreement?: boolean,
    public medicalFitnessAgreement?: boolean
  ) {
    this.termsAgreement = this.termsAgreement || false;
    this.gdprAgreement = this.gdprAgreement || false;
    this.photographyAgreement = this.photographyAgreement || false;
    this.marketingAgreement = this.marketingAgreement || false;
    this.medicalFitnessAgreement = this.medicalFitnessAgreement || false;
  }
}
