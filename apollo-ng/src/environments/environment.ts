// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const backendUrl = 'http://localhost:9090';

export const environment = {
    production: false,
    apiUrl: backendUrl + '/api',
    backendUrl,
    logoutUrl: 'http://ludarous.com:2080/auth/realms/dev/protocol/openid-connect/logout',
    allowedLoginReferrers: ['*'],
};