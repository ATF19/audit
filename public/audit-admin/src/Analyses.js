import React from 'react';
import { List, Datagrid, RadioButtonGroupInput, UrlField, SimpleForm, DateField, TextField, EditButton, DisabledInput, TextInput, LongTextInput, DateInput } from 'admin-on-rest';

import CustomUrl from "./CustomUrl";

export const AnalyseList = (props) => {
  console.log(props);
    return (
      <List {...props}>
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
