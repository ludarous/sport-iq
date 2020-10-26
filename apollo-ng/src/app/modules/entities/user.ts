import { AuthoritiesBase } from './enums/authorities-base';
import { Athlete, IAthlete } from '../../dashboard/entities/model/athlete.model';

export interface IUser {
    id: string;
    login?: string;
    firstName?: string;
    lastName?: string;
    email?: string;
    authorities?: Array<AuthoritiesBase>;

    activated?: boolean;
    createdDate?: string;

    imageUrl?: string;
    idpId?: string;

    langKey?: string;
    athlete?: IAthlete;

}


export class User implements IUser {
    id: string;
    login: string;
    firstName: string;
    lastName: string;
    email: string;
    authorities?: Array<AuthoritiesBase>;

    activated: boolean;
    createdDate: string;

    langKey: string;
    athlete?: IAthlete;

    imageUrl: string;
    idpId: string;


    static parseEnums(item: IUser, appAuthoritiesEnum: any): IUser {
        if (item) {
            const authorities = new Array<AuthoritiesBase>();
            if (item.authorities) {
                for (const authority of item.authorities) {
                    authorities.push(new AuthoritiesBase(appAuthoritiesEnum, <any>authority));
                }
            }

            if (item.athlete) {
                item.athlete = Athlete.parseItemEnums(item.athlete);
            }

            item.authorities = authorities;
        }
        return item;
    }
}
