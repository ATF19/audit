import React from 'react';
import { Filter, List, Datagrid, RadioButtonGroupInput, UrlField, SimpleForm, DateField, TextField, EditButton, DisabledInput, TextInput, LongTextInput, DateInput } from 'admin-on-rest';

import CustomUrl from "./CustomUrl";

const AnalyseFilter = (props) => (
    <Filter {...props}>
        <TextInput label="Recherche par utilisateur..." source="q" alwaysOn />
    </Filter>
);

export const AnalyseList = (props) => {
  console.log(props);
    return (
      <List {...props} filters={<AnalyseFilter />}>
          <Datagrid>
              <TextField source="id" label="#"/>
              <TextField source="norme.organisation" label="Organisation" />
              <TextField source="norme.numero" label="Norme" />
              <TextField source="utilisateur.email" label="Createur" />
              <CustomUrl source="rapport" label="Rapport" />
              <DateField source="createdAt" label="Date d'ajout" />
              <DateField source="updatedAt" label="Date de mise a jour" />
          </Datagrid>
      </List>
  );
  }

const AnalyseTitle = ({ record }) => {
    return <span>Analyse {record ? `"${record.id}"` : ''}</span>;
};
