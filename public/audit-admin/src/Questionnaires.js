import React from 'react';
import { Filter, List, ReferenceManyField, ReferenceInput, ReferenceField, required, SelectInput, Datagrid, Edit, Create, SimpleForm, DateField, TextField, EditButton, DisabledInput, TextInput, LongTextInput, DateInput } from 'admin-on-rest';


export const QuestionnaireList = (props) => (
    <List {...props}>
        <Datagrid>
            <TextField source="id" label="#"/>
            <TextField source="responsable.titre" label="Responsable" />
            <TextField source="question" label="Question" />
            <DateField source="createdAt" label="Date d'ajout" />
            <DateField source="updatedAt" label="Date de mise a jour" />
            <EditButton basePath="/Questionnaires" />
        </Datagrid>
    </List>
);

const QuestionnaireTitle = ({ record }) => {
    return <span>Questionnaire {record ? `"${record.responsable.titre}: ${record.question}"` : ''}</span>;
};

export const QuestionnaireEdit = (props) => (
    <Edit title={<QuestionnaireTitle />} {...props}>
        <SimpleForm>
            <DisabledInput source="id" />
            <DisabledInput source="responsable.titre" label="Responsable" />
            <TextInput source="question" />
        </SimpleForm>
    </Edit>
);

export const QuestionnaireCreate = (props) => (
    <Create title="Ajouter question" {...props}>
        <SimpleForm>
          <LongTextInput source="question" />
          <ReferenceInput label="Responsable" source="responsable['id']" reference="responsables" validate={required} allowEmpty>
                <SelectInput optionText="titre" />
            </ReferenceInput>
        </SimpleForm>
    </Create>
);
