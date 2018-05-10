import React from 'react';
import { Filter, List, ReferenceInput, ReferenceField, SelectArrayInput, ReferenceArrayInput, required, SelectInput, Datagrid, Edit, Create, SimpleForm, DateField, TextField, EditButton, DisabledInput, TextInput, LongTextInput, DateInput } from 'admin-on-rest';

const ExigenceFilter = (props) => (
    <Filter {...props}>
        <TextInput label="Recherche par clause..." source="q" alwaysOn />
    </Filter>
);

export const ExigenceList = (props) => (
    <List {...props} filters={<ExigenceFilter />}>
        <Datagrid>
            <TextField source="id" label="#"/>
            <ReferenceField label="Clause" source="clause.id" reference="clauses">
                <TextField source="libelle" label="Clause" />
            </ReferenceField>
            <TextField source="reference" label="Reference" />
            <TextField source="libelle" label="Libelle" />
            <DateField source="createdAt" label="Date d'ajout" />
            <DateField source="updatedAt" label="Date de mise a jour" />
            <EditButton basePath="/Exigences" />
        </Datagrid>
    </List>
);

const ExigenceTitle = ({ record }) => {
    return <span>Exigence {record ? `"${record.id}"` : ''}</span>;
};

export const ExigenceEdit = (props) => (
    <Edit title={<ExigenceTitle />} {...props}>
        <SimpleForm>
            <DisabledInput source="id" />
            <DisabledInput source="clause.libelle" label="Clause" />
            <TextInput source="reference" />
            <TextInput source="libelle" />
        </SimpleForm>
    </Edit>
);

export const ExigenceCreate = (props) => (
    <Create title="Create a Exigence" {...props}>
        <SimpleForm>
          <TextInput source="reference" />
          <TextInput source="libelle" />
          <ReferenceInput label="Clause" source="clause['id']" reference="clauses" validate={required} allowEmpty>
                <SelectInput optionText="libelle" />
            </ReferenceInput>
            <ReferenceArrayInput label="Responsable" source="responsables" reference="responsables" allowEmpty>
                  <SelectArrayInput optionText="titre" />
              </ReferenceArrayInput>
        </SimpleForm>
    </Create>
);
