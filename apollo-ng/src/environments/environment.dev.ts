export const backendUrl = 'https://api.dev.sportiq.cz';

export const environment = {
    production: true,
    apiUrl: backendUrl + '/api',
    backendUrl,
    logoutUrl: 'https://auth.dev.sportiq.cz/auth/realms/dev/protocol/openid-connect/logout',
    allowedLoginReferrers: ['*'],
};
