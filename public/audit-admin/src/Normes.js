import React from 'react';
import { Filter, ReferenceInput, SelectInput, List, Datagrid, Edit, Create, SimpleForm, DateField, TextField, EditButton, DisabledInput, TextInput, LongTextInput, DateInput } from 'admin-on-rest';

const NormeFilter = (props) => (
    <Filter {...props}>
        <TextInput label="Recherche..." source="q" alwaysOn />
        <ReferenceInput label="Norme" source="id" reference="normes">
            <SelectInput optionText="norme" />
        </ReferenceInput>
    </Filter>
);

export const NormeList = (props) => (
    <List {...props} filters={<NormeFilter />}>
        <Datagrid>
            <TextField source="id" label="#"/>
            <TextField source="organisation" label="Organisation" />
            <TextField source="numero" label="Norme" />
            <DateField source="createdAt" label="Date d'ajout" />
            <DateField source="updatedAt" label="Date de mise a jour" />
            <EditButton basePath="/Normes" />
        </Datagrid>
    </List>
);

const NormeTitle = ({ record }) => {
    return <span>Norme {record ? `"${record.numero}"` : ''}</span>;
};

export const NormeEdit = (props) => (
    <Edit title={<NormeTitle />} {...props}>
        <SimpleForm>
            <DisabledInput source="id" />
            <TextInput source="organisation" />
            <TextInput source="numero" />
        </SimpleForm>
    </Edit>
);

export const NormeCreate = (props) => (
    <Create title="Create a Norme" {...props}>
        <SimpleForm>
          <TextInput source="organisation" />
          <TextInput source="numero" />
        </SimpleForm>
    </Create>
);
