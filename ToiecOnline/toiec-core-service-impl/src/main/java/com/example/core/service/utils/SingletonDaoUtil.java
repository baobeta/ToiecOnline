package com.example.core.service.utils;

import com.example.core.daoimpl.*;

public class SingletonDaoUtil {
    private static UserDaoImpl userDaoImpl = null;
    private static RoleDaoImpl roleDaoImpl = null;
    private static ListenGuideLineImpl listenGuideLineDaoImpl = null;
    private static CommentDaoImpl commentDaoImpl = null;
    private static ExaminationDaoImpl examinationDaoImpl = null;
    private static ExaminationQuestionDaoImpl examinationQuestionDaoImpl = null;
    private static ExerciseDaoImpl exerciseDaoImpl = null;
    private static ExerciseQuestionDaoImpl exerciseQuestionDaoImpl = null;
    private static ResultDaoImpl resultDaoImpl = null;
    public static  UserDaoImpl getUserDaoInstance() {
        if(userDaoImpl == null) {
            userDaoImpl = new UserDaoImpl();

        }
        return userDaoImpl;
    }

    public static  RoleDaoImpl getRoleDaoInstance() {
        if(roleDaoImpl == null) {
            roleDaoImpl = new RoleDaoImpl();

        }
        return roleDaoImpl;
    }

    public static  ListenGuideLineImpl getListenGuidelineDaoInstance() {
        if(listenGuideLineDaoImpl == null) {
            listenGuideLineDaoImpl = new ListenGuideLineImpl();

        }
        return listenGuideLineDaoImpl;
    }
    public static CommentDaoImpl getCommentDaoInstance() {
        if (commentDaoImpl == null) {
            commentDaoImpl = new CommentDaoImpl();
        }
        return commentDaoImpl;
    }

    public static ExaminationDaoImpl getExaminationDaoInstance() {
        if (examinationDaoImpl == null) {
            examinationDaoImpl = new ExaminationDaoImpl();
        }
        return examinationDaoImpl;
    }

    public static ExaminationQuestionDaoImpl getExaminationQuestionDaoInstance() {
        if (examinationQuestionDaoImpl == null) {
            examinationQuestionDaoImpl = new ExaminationQuestionDaoImpl();
        }
        return examinationQuestionDaoImpl;
    }

    public static ExerciseDaoImpl getExerciseDaoInstance() {
        if (exerciseDaoImpl == null) {
            exerciseDaoImpl = new ExerciseDaoImpl();
        }
        return exerciseDaoImpl;
    }

    public static ExerciseQuestionDaoImpl getExerciseQuestionDaoInstance() {
        if (exerciseQuestionDaoImpl == null) {
            exerciseQuestionDaoImpl = new ExerciseQuestionDaoImpl();
        }
        return exerciseQuestionDaoImpl;
    }

    public static ResultDaoImpl getResultDaoInstance() {
        if (resultDaoImpl == null) {
            resultDaoImpl = new ResultDaoImpl();
        }
        return resultDaoImpl;
    }


}
