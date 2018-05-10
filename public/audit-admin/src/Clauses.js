import React from 'react';
import { Filter, List, ReferenceManyField, ReferenceInput, ReferenceField, required, SelectInput, Datagrid, Edit, Create, SimpleForm, DateField, TextField, EditButton, DisabledInput, TextInput, LongTextInput, DateInput } from 'admin-on-rest';

const ClauseFilter = (props) => (
    <Filter {...props}>
        <TextInput label="Recherche par norme..." source="q" alwaysOn />
    </Filter>
);

export const ClauseList = (props) => (
    <List {...props} filters={<ClauseFilter />}>
        <Datagrid>
            <TextField source="id" label="#"/>
            <TextField source="norme.organisation" label="Organisation" />
            <TextField source="norme.numero" label="Norme" />
            <TextField source="libelle" label="Libelle" />
            <DateField source="createdAt" label="Date d'ajout" />
            <DateField source="updatedAt" label="Date de mise a jour" />
            <EditButton basePath="/Clauses" />
        </Datagrid>
    </List>
);

const ClauseTitle = ({ record }) => {
    return <span>Clause {record ? `"${record.norme.organisation}: ${record.norme.numero} ${record.libelle}"` : ''}</span>;
};

export const ClauseEdit = (props) => (
    <Edit title={<ClauseTitle />} {...props}>
        <SimpleForm>
            <DisabledInput source="id" />
            <DisabledInput source="norme.organisation" label="Organisation" />
            <DisabledInput source="norme.numero" label="Norme" />
            <LongTextInput source="libelle" />
        </SimpleForm>
    </Edit>
);

export const ClauseCreate = (props) => (
    <Create title="Create a Clause" {...props}>
        <SimpleForm>
          <LongTextInput source="libelle" />
          <ReferenceInput label="Norme" source="norme['id']" reference="normes" validate={required} allowEmpty>
                <SelectInput optionText="numero" />
            </ReferenceInput>
        </SimpleForm>
    </Create>
);
