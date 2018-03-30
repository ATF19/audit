import React from 'react';
import { List, ReferenceInput, ReferenceField, required, SelectInput, Datagrid, Edit, Create, SimpleForm, DateField, TextField, EditButton, DisabledInput, TextInput, LongTextInput, DateInput } from 'admin-on-rest';

export const ResponsableList = (props) => (
    <List {...props}>
        <Datagrid>
            <TextField source="id" label="#"/>
            <TextField source="titre" label="Titre" />
            <DateField source="createdAt" label="Date d'ajout" />
            <DateField source="updatedAt" label="Date de mise a jour" />
            <EditButton basePath="/Responsables" />
        </Datagrid>
    </List>
);

const ResponsableTitle = ({ record }) => {
    return <span>Responsable {record ? `"${record.titre}"` : ''}</span>;
};

export const ResponsableEdit = (props) => (
    <Edit title={<ResponsableTitle />} {...props}>
        <SimpleForm>
            <DisabledInput source="id" />
            <TextInput source="titre" />
        </SimpleForm>
    </Edit>
);

export const ResponsableCreate = (props) => (
    <Create title="Create a Responsable" {...props}>
        <SimpleForm>
          <TextInput source="titre" />
        </SimpleForm>
    </Create>
);
