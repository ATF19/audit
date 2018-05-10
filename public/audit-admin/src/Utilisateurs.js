import React from 'react';
import { Filter, List, Datagrid, RadioButtonGroupInput, Edit, Create, SimpleForm, DateField, TextField, EditButton, DisabledInput, TextInput, LongTextInput, DateInput } from 'admin-on-rest';

const UtilisateurFilter = (props) => (
    <Filter {...props}>
        <TextInput label="Recherche par email..." source="q" alwaysOn />
    </Filter>
);

export const UtilisateurList = (props) => (
    <List {...props} filters={<UtilisateurFilter />}>
        <Datagrid>
            <TextField source="id" label="#"/>
            <TextField source="email" label="Email" />
            <TextField source="role" label="Role" />
            <DateField source="createdAt" label="Date d'ajout" />
            <DateField source="updatedAt" label="Date de mise a jour" />
            <EditButton basePath="/Utilisateurs" />
        </Datagrid>
    </List>
);

const UtilisateurTitle = ({ record }) => {
    return <span>Utilisateur {record ? `"${record.email}"` : ''}</span>;
};

export const UtilisateurEdit = (props) => (
    <Edit title={<UtilisateurTitle />} {...props}>
        <SimpleForm>
            <DisabledInput source="id" />
            <TextInput source="email" type="email" />
            <TextInput source="password" type="password" />
            <RadioButtonGroupInput source="role" choices={[
                { id: 'simple', name: 'Simple' },
                { id: 'admin', name: 'Admin' }
            ]} />
        </SimpleForm>
    </Edit>
);

export const UtilisateurCreate = (props) => (
    <Create title="Create a Utilisateur" {...props}>
        <SimpleForm>
          <TextInput source="email" type="email" />
          <TextInput source="password" type="password" />
          <RadioButtonGroupInput source="role" choices={[
              { id: 'simple', name: 'Simple' },
              { id: 'admin', name: 'Admin' }
          ]} />
        </SimpleForm>
    </Create>
);
