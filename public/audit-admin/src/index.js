import React from 'react';
import { render } from 'react-dom';
import { jsonServerRestClient, Admin, Resource, Delete, fetchUtils} from 'admin-on-rest';
import frenchMessages from 'aor-language-french';

import { NormeList, NormeEdit, NormeCreate, NormeIcon } from './Normes';
import { ClauseList, ClauseEdit, ClauseCreate, ClauseIcon } from './Clauses';
import { UtilisateurList, UtilisateurEdit, UtilisateurCreate, UtilisateurIcon } from './Utilisateurs';
import { ExigenceList, ExigenceEdit, ExigenceCreate, ExigenceIcon } from './Exigences';
import { ResponsableList, ResponsableEdit, ResponsableCreate, ResponsableIcon } from './Responsables';

import Auth from "./Auth";
import Config from "./Config";

const messages = {
    fr: frenchMessages,
};

const httpClient = (url, options = {}) => {
    if (!options.headers) {
        options.headers = new Headers({ Accept: 'application/json' });
    }
    const token = localStorage.getItem('token');
    options.headers.set('Authorization', `Bearer ${token}`);
    return fetchUtils.fetchJson(url, options);
}
const restClient = jsonServerRestClient(Config.api, httpClient);

render(
    <Admin authClient={Auth} title="Alliacom audit" restClient={restClient} locale="fr" messages={messages}>
      <Resource name="normes" list={NormeList} edit={NormeEdit} create={NormeCreate} remove={Delete} />
      <Resource name="clauses" list={ClauseList} edit={ClauseEdit} create={ClauseCreate} remove={Delete} />
      <Resource name="exigences" list={ExigenceList} edit={ExigenceEdit} create={ExigenceCreate} remove={Delete} />
      <Resource name="responsables" list={ResponsableList} edit={ResponsableEdit} create={ResponsableCreate} remove={Delete} />
      <Resource name="utilisateurs" list={UtilisateurList} edit={UtilisateurEdit} create={UtilisateurCreate} remove={Delete} />
    </Admin>,
    document.getElementById('root')
);
