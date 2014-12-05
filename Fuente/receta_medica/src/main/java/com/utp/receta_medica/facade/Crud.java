package com.utp.receta_medica.facade;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.apache.commons.lang3.StringUtils.uncapitalize;
import org.reflections.ReflectionUtils;
import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.withAnnotation;

/**
 *
 * @author brahyam
 */
public abstract class Crud {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    protected abstract EntityManager getEntityManager();

    //****************************
    // Metodos de acceso a la base de datos
    //****************************
    /**
     * Guarda y/o Actualiza el registro en la base de datos.<br/><br/>
     * (Nota: la objeto entidad que retorn se encuentra ligada la base de datos
     * y los cambios realizados este objeto se veran reflejados directamente en
     * esta.)
     *
     * @param <T> Tipo de la entidad.
     * @param entity Entidad enviada para se registrada en la BD.
     * @return La entidad registrada.
     */
    public <T> T save(T entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if (constraintViolations.size() > 0) {
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> cv = iterator.next();
                System.err.println(cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage());

//            JsfUtil.addErrorMessage(cv.getRootBeanClass().getSimpleName()+"."+cv.getPropertyPath() + " " +cv.getMessage());
            }
        } else {
            return getEntityManager().merge(entity);
        }
        return null;
    }

    /**
     * Elimina el registro de la base de datos, si este existe.
     *
     * @param <T> el tipo de la entidad
     * @param entity entidad que se desea borrar
     * @throws Exception si el objeto no esta en la base de datos o esta mal
     * definido en paarmetro.
     */
    public <T> void remove(T entity) throws Exception {
        if (find(entity) != null) {
            getEntityManager().remove(find(entity));
        }
    }

    /**
     * Encuentra el elemento en la base de datos a partir de la PrimaryKey
     * enviada en la <code>entity</code>.
     *
     * @param <T> tipo de la entidad.
     * @param entity Entidad que se quiere buscar.
     * @return El registro de la base de datos, si se encuentra.
     * @throws Exception si el parametro entregado es <code>null</code> o si la
     * Pk esta parcial o completamente <code>null</code>.
     */
    public <T> T find(T entity) throws Exception {

        if (entity == null) {
            throw new NullPointerException("La entidad se encuentra vacia.");
        }

        Object valorPK = null;
        Class<?> claseEnt = entity.getClass();

        try {
            Field campoPK = claseEnt.getDeclaredField(uncapitalize(claseEnt.getSimpleName()) + "PK");
            campoPK.setAccessible(true);
            valorPK = campoPK.get(entity);

            if (valorPK != null) {
                for (Field campo : campoPK.getType().getFields()) {
                    campo.setAccessible(true);
                    if (campo.get(valorPK) == null) {
                        throw new NullPointerException("La PrimaryKey de la entidad se encuentra parcialmente vacia.");
                    }
                }
            }

        } catch (NoSuchFieldException ex) {

            Set<Field> setCampoPk = getAllFields(claseEnt, withAnnotation(Id.class));

            for (Field campoPrimitivo : setCampoPk) {
                campoPrimitivo.setAccessible(true);
                valorPK = campoPrimitivo.get(entity);
            }
        }

        if (valorPK == null) {
            throw new NullPointerException("La PrimaryKey de la entidad se encuentra vacia.");
        }

        return (T) getEntityManager().find(claseEnt, valorPK);
    }

    /**
     * Encuentra todos los registros de la tabla asociada que cumplan con los
     * valor enviados en la clase entidad.<br/>
     * Si no se le asignan valores, es decir <code>null</code>, a la clase
     * entidad se retorna la lista todos los registros de la tabla asociada.
     *
     * @param <T> Clase de la Entidad entregada
     * @param entity Entidad que contiene los valores a comparar.
     * @return la lista con registros entregados.
     * @throws Exception si el parametro entregado es <code>null</code>, o si el
     * paramtro no es una Clase entidad.
     */
    public <T> List<T> findAll(T entity) throws Exception {

        if (entity == null) {
            throw new NullPointerException("La entidad se encuentra vacia.");
        }

        if (entity.getClass().getSimpleName().matches("\\w*PK")) {
            throw new IllegalArgumentException("El parametro no es una entidad.");
        }

        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<? extends Object> cq = cb.createQuery(entity.getClass());
        Root<? extends Object> entFrom = cq.from(entity.getClass());

        // Lista de Expresiones para la CriteriaQuery
        List<Predicate> whereRestricciones = new ArrayList<>();
        List<Field> campos = new ArrayList<>();

        // Se buscan los campos de la entidad
        Set<Field> camposPK = ReflectionUtils.getAllFields(entity.getClass(), withAnnotation(EmbeddedId.class));
        Object valorPK;
        if (!camposPK.isEmpty()) {
            ((Field) camposPK.toArray()[0]).setAccessible(true);
            valorPK = ((Field) camposPK.toArray()[0]).get(entity);
            Class<?> claseEmb = ((Field) camposPK.toArray()[0]).getType();
            camposPK = ReflectionUtils.getAllFields(claseEmb, withAnnotation(Column.class));
        } else {
            //Si no tiene clase PK
            valorPK = entity;
            //campos.addAll(ReflectionUtils.getAllFields(entity.getClass(), withAnnotation(Id.class)));
        }

        // Se Addicionan los campos en una sola estructura
        campos.addAll(ReflectionUtils.getAllFields(entity.getClass(), withAnnotation(Column.class)));
        campos.addAll(ReflectionUtils.getAllFields(entity.getClass(), withAnnotation(JoinColumn.class)));
        campos.addAll(ReflectionUtils.getAllFields(entity.getClass(), withAnnotation(JoinColumns.class)));

        // Se itera sobre los campos PK ( su exclusivida se debe por que en RunTime se desconoce si la PK es una Clase 
        if (valorPK != null) {
            for (Field campoPK : camposPK) {
                campoPK.setAccessible(true);
                Object valorCampo = campoPK.get(valorPK);
                if (valorCampo != null) {
                    Expression<?> expresion = entFrom.get(uncapitalize(campoPK.getDeclaringClass().getSimpleName())).get(campoPK.getName());
                    whereRestricciones.add(cb.and(cb.equal(expresion, valorCampo)));
                }
            }
        }

        // Se itera por el resto de campos para obtener los valores, si la clase no tiene PK
        for (Field campo : campos) {
            campo.setAccessible(true);
            Object valorCampo = campo.get(entity);
            if (valorCampo != null) {
                Expression<?> expresion = entFrom.get(campo.getName());
                whereRestricciones.add(cb.and(cb.equal(expresion, valorCampo)));
            }
        }

        // Pasar un arrayList a Vector
        Predicate[] re = new Predicate[]{};
        re = whereRestricciones.toArray(re);

        if (re.length != 0) {
            cq.where(cb.and(re));
        }

//        TypedQuery qw = getEntityManager().createQuery(cq);
//        qw.getResultList();
//        String a = qw.unwrap(org.eclipse.persistence.jpa.JpaQuery.class).getDatabaseQuery().getSQLString();
        // Se termina la CriteriQuery y se ejecuta
        //cq.select((Selection) entFrom);
        return (List<T>) getEntityManager().createQuery(cq).getResultList();
    }

//    Por si se necesitan
//    public <T> List<T> findRange(T entity, int[] range) {
//        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        cq.select(cq.from(entity.getClass()));
//        javax.persistence.Query q = getEntityManager().createQuery(cq);
//        q.setMaxResults(range[1] - range[0] + 1);
//        q.setFirstResult(range[0]);
//        return q.getResultList();
//    }
//
//    public <T> int count(T entity) {
//        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        javax.persistence.criteria.Root<T> rt = cq.from(entity.getClass());
//        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
//        javax.persistence.Query q = getEntityManager().createQuery(cq);
//        return ((Long) q.getSingleResult()).intValue();
//    }
    /**
     * Metodo para la autogeneracion del consecutivo de la Primary Key a una
     * Clase entidad.<br/><br/>
     * Para que se pueda hacer la generacion del consecutivo se debe tener en
     * cuenta las siguientes condiciones para la intancia de la Clase entidad:
     * <ul>
     * - <il>Si la Clase entidad es <code>null</code> se levantra una exception
     * de <code>NullPointerException</code>
     * </ul>
     * <ul>
     * - <il>Si la Clase entidad no tiene llave compuesta, es decir tiene un
     * unico campo con anotacion <code>@Id</code>, el campo Id debe ser
     * <code>null</code>, de lo contrario el valor que tenga sera reemplazado
     * por el siguiente valor en la tabla.</il></br>
     * </ul>
     * <ul>
     * - <il>Si la Clase entidad tiene llave compuesta y su valor es
     * <code>null</code>, se levantara una exception de
     * <code>IllegalArgumentException</code>.</il></br>
     * </ul>
     * <ul>
     * - <il>
     * Si el valor de la llave compuesta no es <code>null</code>: Solamente
     * <b>debe</b> haber una llave primaria con valor <code>null</code> del
     * conjunto de llaves primarias, y esta sera a la cual se leasginara el
     * valor generado. Si llega mas de una Llave primaria con valor
     * <code>null</code> se levantara una exception de
     * <code>IllegalArgumentException</code>.
     * </il>
     * </ul>
     *
     * @param <T> tipo de la entidad.
     * @param entity la entidad que se envia.
     * @throws Exception si el paramatero es <code>null</code>, si la instancia
     * esta mal definida o si el parametro envidado no es de clase entidad.
     */
    public <T> void generarConsecutivo(T entity) throws Exception {

        // Se reviza si la entidad no es null
        if (entity == null) {
            throw new NullPointerException("La entidad se encuentra vacia.");
        }

        Field campoSelect = null;

        // Se buscan los campos de la entidad
        Set<Field> camposPK = ReflectionUtils.getAllFields(entity.getClass(), withAnnotation(EmbeddedId.class));
        if (!camposPK.isEmpty()) {
            // -- Si tiene llave compuesta
            ((Field) camposPK.toArray()[0]).setAccessible(true);
            Object valorPK = ((Field) camposPK.toArray()[0]).get(entity);
            Class<?> claseEmb = ((Field) camposPK.toArray()[0]).getType();
            camposPK = ReflectionUtils.getAllFields(claseEmb, withAnnotation(Column.class));

            // Si el valor de la PK viene nulo en la entidad.
            if (valorPK == null) {
                throw new IllegalArgumentException("Valor PK mal definido.");
            }

            Set<Field> campoParameter = new HashSet<>();

            // Se revisa el campo null para asignar a campoSelect
            Field[] arrayCampos = camposPK.toArray(new Field[]{});
            Field.setAccessible(arrayCampos, true);
            int maxNull = 0;
            for (Field campo : arrayCampos) {
                Object valorCampo = campo.get(valorPK);
                if (valorCampo == null) {
                    maxNull++;
                    campoSelect = campo;
                } else {
                    campoParameter.add(campo);
                }

                if (maxNull >= 2) {
                    throw new IllegalArgumentException("Valor PK mal definido.");
                }
            }

            if (campoSelect == null) {
                throw new IllegalArgumentException("Valor PK mal definido.");
            }

            // Prepare Query Builder con los parametros y setValue
            javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            javax.persistence.criteria.CriteriaQuery<? extends Object> cq = cb.createQuery(entity.getClass());
            Root<? extends Object> queryFrom = cq.from(entity.getClass());

            List<Predicate> whereRestricciones = new ArrayList<>();

            // Se adicionan los where correspondientes
            for (Field campo : campoParameter) {
                campo.setAccessible(true);
                Object valorCampo = campo.get(valorPK);
                Expression<?> expresion = queryFrom.get(uncapitalize(campo.getDeclaringClass().getSimpleName())).get(campo.getName());
                whereRestricciones.add(cb.and(cb.equal(expresion, valorCampo)));
            }

            Predicate[] re = whereRestricciones.toArray(new Predicate[]{});

            if (re.length != 0) {
                cq.where(cb.and(re));
            }

            Expression expresion = queryFrom.get(uncapitalize(campoSelect.getDeclaringClass().getSimpleName())).get(campoSelect.getName());
            cq.select(cb.max(expresion));

            // Asigna el siguiente valor
            TypedQuery qw = getEntityManager().createQuery(cq);
            campoSelect.set(valorPK, 1 + (Integer) ((qw.getSingleResult() == null) ? 0 : qw.getSingleResult()));

        } else {
            // -- Si no tiene llave compuesta
            Set<Field> campoId = ReflectionUtils.getAllFields(entity.getClass(), withAnnotation(Id.class));
            if (campoId.isEmpty()) {
                throw new IllegalArgumentException("El parametro no es entidad o no tiene definido un campo Id");
            }

            campoSelect = (Field) campoId.toArray()[0];
            campoSelect.setAccessible(true);

            javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            javax.persistence.criteria.CriteriaQuery<? extends Object> cq = cb.createQuery(entity.getClass());
            Root<? extends Object> queryFrom = cq.from(entity.getClass());

            Expression expresion = queryFrom.get(campoSelect.getName());
            cq.select(cb.max(expresion));

            TypedQuery qw = getEntityManager().createQuery(cq);
            //System.out.println("Eso="+qw.getSingleResult());
            campoSelect.set(entity, 1 + (Integer) ((qw.getSingleResult() == null) ? 0 : qw.getSingleResult()));
        }
    }
}
